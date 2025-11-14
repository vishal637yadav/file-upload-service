package com.code.file.upload.dsa.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class LRUCache<K, V> implements LRU<K, V> {

    private static final int DEFAULT_CACHE_SIZE = 4;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node(" + key + ", " + value + ", Prev-" + (prev != null ? prev.key : null) + ", Next-" + (next != null ? next.key : null) + ")";
        }
    }

    private void addToFront(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);

        if (nodeMap.isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        nodeMap.put(key, newNode);
    }

    private Node<K, V> removeFromTail() {
        Node<K, V> removedNode = tail;

        tail.prev.next = null;
        tail = tail.prev;

        return nodeMap.remove(removedNode.key);
    }

    //  |.|5|.| --> |.|5|.| --> |.|5|.| --> |.|5|.|
    private Node<K, V> cacheLookUp(K key) {
        // return null, if below conditions meat
        if (nodeMap.isEmpty() || !nodeMap.containsKey(key)) {
            return null;
        }

        //Node already Exists
        Node<K, V> node = nodeMap.get(key);

        //If Reading Head
        if (head == node) {
            return head;
        }

        //If Reading Tail
        if (tail == node) {
            // Updating pointers
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            // Updating pointers
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
        }

        // Add to Front
        head.prev = node;
        node.next = head;
        head = node;

        return head;
    }

    private Node<K, V> head;
    private Node<K, V> tail;
    private final Map<K, Node<K, V>> nodeMap;

    @Getter
    @Setter
    private int cacheSize;

    public LRUCache() {
        cacheSize = DEFAULT_CACHE_SIZE;
        nodeMap = new ConcurrentHashMap<>();
    }


    @Override
    public void writeToCache(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot Write to Cache as key is Null");
        }

        log.info("!! Write To Cache !!");
        // add to cache
        this.addToFront(key, value);

        // evict cache
        if (nodeMap.size() > cacheSize) {
            Node<K, V> removedNode = removeFromTail();
            log.info("Remove From Tail!!--------{}", removedNode.toString());
        }
    }

    @Override
    public V readFromCache(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot Read from Cache as key is Null");
        }
        Node<K, V> node = cacheLookUp(key);
        if (node != null) {
            log.info("Read From Cache !! =={}", node);
            return node.value;
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LRUCache:\n");
        sb.append("-----------------------------------\n");
        sb.append("\thead = ").append(head).append(",\n");
        sb.append("\ttail = ").append(tail).append(",\n");

        StringBuilder queueData = new StringBuilder("\n\tqueueData = { ");
        int size = nodeMap.size();
        Node<K, V> curNode = head;
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                queueData.append(curNode);
            } else {
                queueData.append(" --> ").append(curNode);
            }
            curNode = curNode.next;
        }
        queueData.append("\n}\n");
        sb.append(queueData);
        sb.append("\n\tsize= ").append(nodeMap.size());
        sb.append("\n-----------------------------------\n");

        return sb.toString();
    }


}
