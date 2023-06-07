package uz.pdp.cambridgelc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.course.CourseEntity;

import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    void deleteCourseEntityByTitle(String title);
}
