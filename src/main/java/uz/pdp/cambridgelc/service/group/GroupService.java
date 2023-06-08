package uz.pdp.cambridgelc.service.group;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.GroupNotFoundException;
import uz.pdp.cambridgelc.repository.CourseRepository;
import uz.pdp.cambridgelc.repository.GroupRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public GroupEntity save(GroupCreateDto groupCreateDto){
        UserCreateDto teacherDto = groupCreateDto.getTeacher();
        CourseDto courseDto = groupCreateDto.getCourse();
        UserEntity teacher = userRepository.findUserEntityByUsername(teacherDto.getUsername())
                .orElseThrow(() -> new GroupNotFoundException("Teacher Not Found"));
        CourseEntity course = courseRepository.findByTitle(courseDto.getTitle())
                .orElseThrow(() -> new GroupNotFoundException("Course Not Found"));

        GroupEntity group = modelMapper.map(groupCreateDto, GroupEntity.class);
        group.setTeacher(teacher);
        group.setCourse(course);
        return groupRepository.save(group);
    }

//    public GroupEntity addStudent(UUID groupId,UserEntity student){
//        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
//                () -> new DataNotFoundException("Group not found!")
//        );
//        List<UserEntity> students = groupEntity.getStudents();
//        students.add(student);
//        groupEntity.setStudents(students);
//        return groupRepository.save(groupEntity);
//    }

    public GroupEntity getGroup(UUID groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));
    }

    public GroupEntity update(UUID groupId,String name){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setName(name);
        return groupRepository.save(group);
    }

    public GroupEntity update(UUID groupId, UserEntity newTeacher){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setTeacher(newTeacher);
        return groupRepository.save(group);
    }

//    public GroupEntity update(UUID groupId, List<UserEntity> failedStudents){
//        GroupEntity group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));
//
//        group.setFailedStudents(failedStudents);
//        return  groupRepository.save(group);
//    }

    public List<GroupEntity> getAllGroups(int page,int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        return groupRepository.findAll(pageable).getContent();
    }
}
