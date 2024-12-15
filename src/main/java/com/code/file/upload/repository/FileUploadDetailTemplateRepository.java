package com.code.file.upload.repository;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadDetailTemplateRepository extends JpaRepository<FileUploadDetailTemplate, Long> {


}
