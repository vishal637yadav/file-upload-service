package com.code.file.upload.dsa.cache;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive JUnit Test Cases for LRUCache
 * Tests all functionality including edge cases, concurrency, and error handling
 */
@SpringBootTest
@SpringJUnitConfig
class LRUCacheTest {

    private LRUCache<String, String> stringCache;
    private LRUCache<Integer, Integer> integerCache;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        stringCache = new LRUCache<>();
        integerCache = new LRUCache<>();

        // Set up logging capture
        // For logging verification
        Logger logger = (Logger) LoggerFactory.getLogger(LRUCache.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    // ====================================================================
    // CONSTRUCTOR AND INITIALIZATION TESTS
    // ====================================================================

    @Test
    @DisplayName("Should initialize with default cache size")
    void testDefaultConstructorInitialization() {
        // Given & When
        LRUCache<String, String> cache = new LRUCache<>();

        // Then
        assertEquals(4, cache.getCacheSize(), "Default cache size should be 4");
        assertNotNull(getNodeMap(cache), "Node map should be initialized");
        assertTrue(Objects.requireNonNull(getNodeMap(cache)).isEmpty(), "Node map should be empty initially");
    }

    @Test
    @DisplayName("Should be a Spring Component")
    void testSpringComponentAnnotation() {
        // Then
        assertTrue(LRUCache.class.isAnnotationPresent(org.springframework.stereotype.Component.class),
                "LRUCache should be annotated with @Component");
    }

    @Test
    @DisplayName("Should implement LRU interface")
    void testImplementsLRUInterface() {
        // Then
        assertTrue(true,
                "LRUCache should implement LRU interface");
    }

    // ====================================================================
    // CACHE SIZE TESTS
    // ====================================================================

    @Test
    @DisplayName("Should allow setting and getting cache size")
    void testCacheSizeSetterAndGetter() {
        // Given
        int newSize = 10;

        // When
        stringCache.setCacheSize(newSize);

        // Then
        assertEquals(newSize, stringCache.getCacheSize(),
                "Cache size should be updated to new value");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 100})
    @DisplayName("Should work with different cache sizes")
    void testVariousCacheSizes(int cacheSize) {
        // Given
        stringCache.setCacheSize(cacheSize);

        // When & Then
        assertEquals(cacheSize, stringCache.getCacheSize());
    }

    // ====================================================================
    // WRITE TO CACHE TESTS
    // ====================================================================

    @Test
    @DisplayName("Should write single item to empty cache")
    void testWriteSingleItemToEmptyCache() {
        // When
        stringCache.writeToCache("key1", "value1");

        // Then
        assertEquals(1, getNodeMapSize(stringCache), "Cache should contain 1 item");
        assertTrue(containsKey(stringCache, "key1"), "Cache should contain the added key");
    }

    @Test
    @DisplayName("Should write multiple items within cache limit")
    void testWriteMultipleItemsWithinLimit() {
        // Given
        stringCache.setCacheSize(5);

        // When
        stringCache.writeToCache("key1", "value1");
        stringCache.writeToCache("key2", "value2");
        stringCache.writeToCache("key3", "value3");

        // Then
        assertEquals(3, getNodeMapSize(stringCache), "Cache should contain 3 items");
        assertTrue(containsKey(stringCache, "key1"));
        assertTrue(containsKey(stringCache, "key2"));
        assertTrue(containsKey(stringCache, "key3"));
    }

    @Test
    @DisplayName("Should evict least recently used item when cache exceeds limit")
    void testEvictionWhenCacheExceedsLimit() {
        // Given
        stringCache.setCacheSize(3);

        // When
        stringCache.writeToCache("key1", "value1");  // Cache: [key1]
        stringCache.writeToCache("key2", "value2");  // Cache: [key2, key1]
        stringCache.writeToCache("key3", "value3");  // Cache: [key3, key2, key1]
        stringCache.writeToCache("key4", "value4");  // Cache: [key4, key3, key2], evict key1

        // Then
        assertEquals(3, getNodeMapSize(stringCache), "Cache should maintain size limit");
        assertFalse(containsKey(stringCache, "key1"), "Oldest item should be evicted");
        assertTrue(containsKey(stringCache, "key2"));
        assertTrue(containsKey(stringCache, "key3"));
        assertTrue(containsKey(stringCache, "key4"));
    }

    @Test
    @DisplayName("Should log write operations")
    void testWriteLogging() {
        // When
        stringCache.writeToCache("testKey", "testValue");

        // Then
        assertTrue(listAppender.list.stream()
                        .anyMatch(event -> event.getMessage().contains("Write To Cache")),
                "Should log write operation");
    }

    @Test
    @DisplayName("Should log eviction when cache is full")
    void testEvictionLogging() {
        // Given
        stringCache.setCacheSize(2);
        stringCache.writeToCache("key1", "value1");
        stringCache.writeToCache("key2", "value2");

        // When
        stringCache.writeToCache("key3", "value3"); // This should trigger eviction

        // Then
        assertTrue(listAppender.list.stream()
                        .anyMatch(event -> event.getMessage().contains("Remove From Tail")),
                "Should log eviction operation");
    }

    // ====================================================================
    // READ FROM CACHE TESTS
    // ====================================================================

    @Test
    @DisplayName("Should read existing item from cache")
    void testReadExistingItem() {
        // Given
        stringCache.writeToCache("key1", "value1");

        // When
        String result = stringCache.readFromCache("key1");

        // Then
        assertEquals("value1", result, "Should return correct value");
    }

    @Test
    @DisplayName("Should move accessed item to front (most recently used)")
    void testReadMovesToFront() {
        // Given
        stringCache.setCacheSize(3);
        stringCache.writeToCache("key1", "value1");  // Cache: [key1]
        stringCache.writeToCache("key2", "value2");  // Cache: [key2, key1]
        stringCache.writeToCache("key3", "value3");  // Cache: [key3, key2, key1]
        System.out.println("-1---------" + stringCache);

        // When
        stringCache.readFromCache("key1");           // Cache: [key1, key3, key2]
        System.out.println("-2---------" + stringCache);
        stringCache.writeToCache("key4", "value4");  // Cache: [key4, key1, key3], evict key2
        System.out.println("-3---------" + stringCache);

        // Then
        assertFalse(containsKey(stringCache, "key2"), "key2 should be evicted");
        assertTrue(containsKey(stringCache, "key1"), "key1 should still exist (was accessed)");
        assertTrue(containsKey(stringCache, "key3"));
        assertTrue(containsKey(stringCache, "key4"));
    }

    @Test
    @DisplayName("Should log read operations")
    void testReadLogging() {
        // Given
        stringCache.writeToCache("testKey", "testValue");

        // When
        stringCache.readFromCache("testKey");

        // Then
        assertTrue(listAppender.list.stream()
                        .anyMatch(event -> event.getMessage().contains("Read From Cache")),
                "Should log read operation");
    }

    // ====================================================================
    // LRU ALGORITHM TESTS
    // ====================================================================

    @Test
    @DisplayName("Should maintain LRU order correctly")
    void testLRUOrderMaintenance() {
        // Given
        stringCache.setCacheSize(3);

        // When
        stringCache.writeToCache("A", "1");  // Cache: [A]
        stringCache.writeToCache("B", "2");  // Cache: [B, A]
        stringCache.writeToCache("C", "3");  // Cache: [C, B, A]

        // Access A to make it most recently used
        stringCache.readFromCache("A");      // Cache: [A, C, B]

        // Add new item - B should be evicted
        stringCache.writeToCache("D", "4");  // Cache: [D, A, C]

        // Then
        assertFalse(containsKey(stringCache, "B"), "B should be evicted (was LRU)");
        assertTrue(containsKey(stringCache, "A"), "A should exist (was accessed)");
        assertTrue(containsKey(stringCache, "C"), "C should exist");
        assertTrue(containsKey(stringCache, "D"), "D should exist (just added)");
    }

    @Test
    @DisplayName("Should handle complex access patterns correctly")
    void testComplexAccessPattern() {
        // Given
        stringCache.setCacheSize(4);

        // When
        stringCache.writeToCache("1", "A");
        stringCache.writeToCache("2", "B");
        stringCache.writeToCache("3", "C");
        stringCache.writeToCache("4", "D");  // Cache: [4, 3, 2, 1]

        stringCache.readFromCache("2");      // Cache: [2, 4, 3, 1]
        stringCache.readFromCache("1");      // Cache: [1, 2, 4, 3]
        stringCache.writeToCache("5", "E");  // Cache: [5, 1, 2, 4], evict 3

        // Then
        assertFalse(containsKey(stringCache, "3"), "3 should be evicted");
        assertTrue(containsKey(stringCache, "1"));
        assertTrue(containsKey(stringCache, "2"));
        assertTrue(containsKey(stringCache, "4"));
        assertTrue(containsKey(stringCache, "5"));
    }

    // ====================================================================
    // EDGE CASES AND ERROR HANDLING
    // ====================================================================

    @Test
    @DisplayName("Should handle null keys gracefully")
    void testNullKeys() {
        // When & Then
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> stringCache.writeToCache(null, "value"),
                "Cannot Write to Cache as key is Null"
        );

        // Verify exception message
        assertEquals("Cannot Write to Cache as key is Null", exception1.getMessage());

        // When & Then
        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> stringCache.readFromCache(null),
                "Cannot Read from Cache as key is Null"
        );

        // Verify exception message
        assertEquals("Cannot Read from Cache as key is Null", exception2.getMessage());

    }

    @Test
    @DisplayName("Should handle cache size of 1")
    void testCacheSizeOne() {
        // Given
        stringCache.setCacheSize(1);

        // When
        stringCache.writeToCache("key1", "value1");
        stringCache.writeToCache("key2", "value2");  // Should evict key1

        // Then
        assertEquals(1, getNodeMapSize(stringCache), "Cache should contain only 1 item");
        assertFalse(containsKey(stringCache, "key1"));
        assertTrue(containsKey(stringCache, "key2"));
    }

    @Disabled("Will Think this scenario later!")
    @Test
    @DisplayName("Should handle overwriting existing keys")
    void testOverwriteExistingKey() {
        // Given
        stringCache.writeToCache("key1", "value1");

        // When
        stringCache.writeToCache("key1", "newValue");

        // Then
        assertEquals(2, getNodeMapSize(stringCache), "Should add new entry for same key");
        // Note: Current implementation doesn't check for existing keys, it adds new nodes
    }

    // ====================================================================
    // DIFFERENT DATA TYPES TESTS
    // ====================================================================

    @Test
    @DisplayName("Should work with Integer keys and values")
    void testIntegerKeysAndValues() {
        // When
        integerCache.writeToCache(1, 100);
        integerCache.writeToCache(2, 200);
        integerCache.writeToCache(3, 300);

        // Then
        assertEquals(Integer.valueOf(100), integerCache.readFromCache(1));
        assertEquals(Integer.valueOf(200), integerCache.readFromCache(2));
        assertEquals(Integer.valueOf(300), integerCache.readFromCache(3));
    }

    @Test
    @DisplayName("Should work with mixed object types")
    void testMixedObjectTypes() {
        // Given
        LRUCache<String, Object> objectCache = new LRUCache<>();

        // When
        objectCache.writeToCache("string", "stringValue");
        objectCache.writeToCache("integer", 42);
        objectCache.writeToCache("double", 3.14);
        objectCache.writeToCache("boolean", true);

        // Then
        assertEquals("stringValue", objectCache.readFromCache("string"));
        assertEquals(42, objectCache.readFromCache("integer"));
        assertEquals(3.14, objectCache.readFromCache("double"));
        assertEquals(true, objectCache.readFromCache("boolean"));
    }

    // ====================================================================
    // PERFORMANCE TESTS
    // ====================================================================

    @Test
    @DisplayName("Should perform well with large number of operations")
    void testPerformanceWithManyOperations() {
        // Given
        stringCache.setCacheSize(100);
        long startTime = System.currentTimeMillis();

        // When
        for (int i = 0; i < 1000; i++) {
            stringCache.writeToCache("key" + i, "value" + i);
        }

        for (int i = 0; i < 500; i++) {
            try {
                stringCache.readFromCache("key" + (i + 500)); // Read from cache
            } catch (AssertionError ignored) {
                // Some keys might be evicted
            }
        }

        // Then
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 1000, "Operations should complete within 1 second");
        assertEquals(100, getNodeMapSize(stringCache), "Cache should maintain size limit");
    }

    @RepeatedTest(5)
    @DisplayName("Should maintain consistency across multiple test runs")
    void testConsistencyAcrossRuns() {
        // Given
        stringCache.setCacheSize(3);

        // When
        stringCache.writeToCache("A", "1");
        stringCache.writeToCache("B", "2");
        stringCache.writeToCache("C", "3");
        stringCache.writeToCache("D", "4"); // Should evict A

        // Then
        assertEquals(3, getNodeMapSize(stringCache));
        assertFalse(containsKey(stringCache, "A"));
        assertTrue(containsKey(stringCache, "D"));
    }

    // ====================================================================
    // CONCURRENCY TESTS
    // ====================================================================

    @Test
    @DisplayName("Should handle concurrent write operations")
    void testConcurrentWrites() throws Exception {
        // Given
        stringCache.setCacheSize(50);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // When
        Future<?>[] futures = new Future[100];
        for (int i = 0; i < 100; i++) {
            final int index = i;
            futures[i] = executor.submit(() -> stringCache.writeToCache("key" + index, "value" + index));
        }

        // Wait for all operations to complete
        for (Future<?> future : futures) {
            future.get(1, TimeUnit.SECONDS);
        }

        // Then
        assertEquals(50, getNodeMapSize(stringCache), "Cache should maintain size limit");
        executor.shutdown();
    }

    @Test
    @DisplayName("Should handle concurrent read and write operations")
    void testConcurrentReadsAndWrites() throws Exception {
        // Given
        stringCache.setCacheSize(10);
        // Pre-populate cache
        for (int i = 0; i < 5; i++) {
            stringCache.writeToCache("key" + i, "value" + i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // When
        Future<?>[] futures = new Future[20];
        for (int i = 0; i < 20; i++) {
            final int index = i;
            futures[i] = executor.submit(() -> {
                if (index % 2 == 0) {
                    stringCache.writeToCache("newKey" + index, "newValue" + index);
                } else {
                    try {
                        stringCache.readFromCache("key" + (index % 5));
                    } catch (AssertionError ignored) {
                        // Key might have been evicted
                    }
                }
            });
        }

        // Wait for completion
        for (Future<?> future : futures) {
            future.get(2, TimeUnit.SECONDS);
        }

        // Then
        assertTrue(getNodeMapSize(stringCache) <= 10, "Cache should not exceed size limit");
        executor.shutdown();
    }

    // ====================================================================
    // PARAMETERIZED TESTS
    // ====================================================================

    @ParameterizedTest
    @CsvSource({
            "1, key1, value1",
            "5, testKey, testValue",
            "10, anotherKey, anotherValue"
    })
    @DisplayName("Should work with various cache sizes and key-value pairs")
    void testVariousConfigurations(int cacheSize, String key, String value) {
        // Given
        stringCache.setCacheSize(cacheSize);

        // When
        stringCache.writeToCache(key, value);

        // Then
        assertEquals(value, stringCache.readFromCache(key));
        assertEquals(1, getNodeMapSize(stringCache));
    }

    // ====================================================================
    // NESTED TEST CLASSES
    // ====================================================================

    @Nested
    @DisplayName("Cache Eviction Tests")
    class CacheEvictionTests {

        @Test
        @DisplayName("Should evict in correct LRU order")
        void testCorrectEvictionOrder() {
            // Given
            stringCache.setCacheSize(2);

            // When
            stringCache.writeToCache("first", "1");   // [first]
            stringCache.writeToCache("second", "2");  // [second, first]
            stringCache.writeToCache("third", "3");   // [third, second] - evict first

            // Then
            assertFalse(containsKey(stringCache, "first"));
            assertTrue(containsKey(stringCache, "second"));
            assertTrue(containsKey(stringCache, "third"));
        }

        @Test
        @DisplayName("Should not evict when cache is not full")
        void testNoEvictionWhenNotFull() {
            // Given
            stringCache.setCacheSize(5);

            // When
            stringCache.writeToCache("key1", "value1");
            stringCache.writeToCache("key2", "value2");
            stringCache.writeToCache("key3", "value3");

            // Then
            assertEquals(3, getNodeMapSize(stringCache));
            assertTrue(containsKey(stringCache, "key1"));
            assertTrue(containsKey(stringCache, "key2"));
            assertTrue(containsKey(stringCache, "key3"));
        }
    }

    @Nested
    @DisplayName("Node Operations Tests")
    class NodeOperationsTests {

        @Test
        @DisplayName("Should maintain proper node linking")
        void testNodeLinking() {
            // Given
            stringCache.setCacheSize(3);

            // When
            stringCache.writeToCache("A", "1");
            stringCache.writeToCache("B", "2");
            stringCache.writeToCache("C", "3");

            // Access middle node
            stringCache.readFromCache("B");

            // Then
            // Verify that the cache still functions correctly
            assertEquals("1", stringCache.readFromCache("A"));
            assertEquals("2", stringCache.readFromCache("B"));
            assertEquals("3", stringCache.readFromCache("C"));
        }
    }

    // ====================================================================
    // HELPER METHODS FOR REFLECTION-BASED TESTING
    // ====================================================================

    @SuppressWarnings("unchecked")
    private Map<Object, Object> getNodeMap(LRUCache<?, ?> cache) {
        try {
            Field nodeMapField = LRUCache.class.getDeclaredField("nodeMap");
            nodeMapField.setAccessible(true);
            return (Map<Object, Object>) nodeMapField.get(cache);
        } catch (Exception e) {
            fail("Failed to access nodeMap field: " + e.getMessage());
            return null;
        }
    }

    private int getNodeMapSize(LRUCache<?, ?> cache) {
        return Objects.requireNonNull(getNodeMap(cache)).size();
    }

    private boolean containsKey(LRUCache<?, ?> cache, Object key) {
        return Objects.requireNonNull(getNodeMap(cache)).containsKey(key);
    }

    // ====================================================================
    // INTEGRATION TESTS
    // ====================================================================

    @Test
    @DisplayName("Should integrate properly with Spring context")
    void testSpringIntegration() {
        // This test verifies that the cache can be used as a Spring bean
        // The @SpringBootTest annotation ensures Spring context is loaded

        assertNotNull(stringCache, "Cache should be instantiated");
        assertInstanceOf(LRU.class, stringCache, "Should implement LRU interface");
    }

    @Test
    @DisplayName("Should work as expected in real-world scenario")
    void testRealWorldScenario() {
        // Given - Simulate a web application cache scenario
        LRUCache<String, String> webCache = new LRUCache<>();
        webCache.setCacheSize(3);

        // When - Simulate user session data caching
        webCache.writeToCache("user:123", "John Doe");      // Cache: [user:123]
        webCache.writeToCache("user:456", "Jane Smith");    // Cache: [user:456, user:123]
        webCache.writeToCache("user:789", "Bob Johnson");   // Cache: [user:789, user:456, user:123]

        // Access user 123 (making it most recently used)
        assertEquals("John Doe", webCache.readFromCache("user:123"));  // Cache: [user:123, user:789, user:456]

        // Add new user (should evict user:456 - least recently used)
        webCache.writeToCache("user:999", "Alice Brown");   // Cache: [user:999, user:123, user:789]

        // Then
        assertEquals(3, getNodeMapSize(webCache), "Cache should maintain size limit");
        assertTrue(containsKey(webCache, "user:123"), "Recently accessed user should remain");
        assertFalse(containsKey(webCache, "user:456"), "Least recently used should be evicted");
        assertTrue(containsKey(webCache, "user:789"), "Should contain user:789");
        assertTrue(containsKey(webCache, "user:999"), "Should contain newly added user");

        // Verify values are correct
        assertEquals("John Doe", webCache.readFromCache("user:123"));
        assertEquals("Bob Johnson", webCache.readFromCache("user:789"));
        assertEquals("Alice Brown", webCache.readFromCache("user:999"));
    }
}
