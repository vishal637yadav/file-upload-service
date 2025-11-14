package com.code.file.upload.repository;

import com.code.file.upload.entity.FileUploadHeaderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadHeaderTemplateRepository extends JpaRepository<FileUploadHeaderTemplate, Long> {


    FileUploadHeaderTemplate findByTemplateCode(String templateCode);

    void deleteByTemplateCode(String templateCode);

}
