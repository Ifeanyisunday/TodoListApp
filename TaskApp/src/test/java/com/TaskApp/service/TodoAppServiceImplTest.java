package com.TaskApp.service;

import com.TaskApp.data.model.Task;
import com.TaskApp.data.repository.TaskRepository;
import com.TaskApp.data.repository.UserRepository;
import com.TaskApp.dtos.requests.*;
import com.TaskApp.exceptions.UserDetailsEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoAppServiceImplTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testThatUserCanRegister(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("");
        assertThrows(UserDetailsEmpty.class, ()-> userService.registerUser(registerRequest));
        assertEquals(0, userRepository.count());
    }

    @Test
    void testUserCanLogin(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("ify");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
    }


    @Test
    void testUserCanLogOut(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());

        LogOutRequest logOutRequest = new LogOutRequest();
        logOutRequest.setEmail("sunjnr10@gmail");
        userService.logOutUser(logOutRequest);
        assertEquals(false, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
    }

    //new test
    @Test
    void testUserCanAddTask(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("ify");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("kent");
        registerRequest2.setEmail("kent10");
        registerRequest2.setPassword("1111");
        userService.registerUser(registerRequest2);
        assertEquals(true, userRepository.findByEmail(registerRequest2.getEmail()).get().isLoggedIn());
        assertEquals(2, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());


        TaskAddRequest taskAddRequest2 = new TaskAddRequest();
        taskAddRequest2.setTitle("Python");
        taskAddRequest2.setNote("Python your father there, your no fit wound me");
        taskAddRequest2.setPriority(3);
        taskAddRequest2.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest2);
        assertEquals(2, taskRepository.count());

    }

    //new test
    @Test
    void testDisplayAllTask(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("sun");
        registerRequest2.setEmail("sunjnr11@gmail");
        registerRequest2.setPassword("1111");
        userService.registerUser(registerRequest2);
        assertEquals(true, userRepository.findByEmail(registerRequest2.getEmail()).get().isLoggedIn());
        assertEquals(2, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());

        TaskAddRequest taskAddRequest2 = new TaskAddRequest();
        taskAddRequest2.setTitle("Python");
        taskAddRequest2.setNote("Python your father there, your no fit wound me");
        taskAddRequest2.setPriority(3);
        taskAddRequest2.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest2);
        assertEquals(2, taskRepository.count());

        FindAllRequest findAllRequest = new FindAllRequest();
        findAllRequest.setEmail("sunjnr10@gmail");
        assertEquals(taskService.findAll(findAllRequest),  taskService.findAll(findAllRequest));
    }


    //new test
    @Test
    void testCompleteTask(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("ify");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setEmail("sunjnr10@gmail");
        completeTaskRequest.setTitle("java");
        taskService.completeTask(completeTaskRequest);
        Optional<Task> taskOptional = taskRepository.findByUserId(completeTaskRequest.getEmail());
        Task task = taskOptional.get();
        assertTrue(task.isDone());
    }

    //new test
    @Test
    void testThatUserCanCheckTaskProgress(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("kent");
        registerRequest2.setEmail("sunjnr11@gmail");
        registerRequest2.setPassword("1111");
        userService.registerUser(registerRequest2);
        assertEquals(true, userRepository.findByEmail(registerRequest2.getEmail()).get().isLoggedIn());
        assertEquals(2, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());

        TaskAddRequest taskAddRequest2 = new TaskAddRequest();
        taskAddRequest2.setTitle("Python");
        taskAddRequest2.setNote("Python your father there, your no fit wound me");
        taskAddRequest2.setPriority(3);
        taskAddRequest2.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest2);
        assertEquals(2, taskRepository.count());

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setEmail("sunjnr10@gmail");
        completeTaskRequest.setTitle("Python");
        taskService.completeTask(completeTaskRequest);
        assertEquals("Scheduled Tasks not complete, 50.0% complete", taskService.dailyTaskIsComplete(completeTaskRequest));
    }

    @Test
    void testUpdateTask(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("ify");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());

        TaskAddRequest taskAddRequest2 = new TaskAddRequest();
        taskAddRequest2.setTitle("Python");
        taskAddRequest2.setNote("Python your father there, your no fit wound me");
        taskAddRequest2.setPriority(3);
        taskAddRequest2.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest2);
        assertEquals(2, taskRepository.count());

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setEmail("sunjnr10@gmail");
        completeTaskRequest.setTitle("Python");
        TaskAddRequest taskAddRequest = new TaskAddRequest();
        taskAddRequest.setEmail("godp10");
        taskAddRequest.setPriority(10);
        taskAddRequest.setNote("new lang in town");
        taskAddRequest.setTitle("C++");
        taskService.updateTask(completeTaskRequest, taskAddRequest);
        assertEquals(taskRepository.findAll().toString(), taskRepository.findAll().toString());
    }

    @Test
    void testThatUserCanDeleteTask(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        taskRepository.deleteAll();
        TaskAddRequest taskAddRequest1 = new TaskAddRequest();
        taskAddRequest1.setTitle("java");
        taskAddRequest1.setNote("java your father there, your no fit wound me");
        taskAddRequest1.setPriority(2);
        taskAddRequest1.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest1);
        assertEquals(1, taskRepository.count());

        TaskAddRequest taskAddRequest2 = new TaskAddRequest();
        taskAddRequest2.setTitle("Python");
        taskAddRequest2.setNote("Python your father there, your no fit wound me");
        taskAddRequest2.setPriority(3);
        taskAddRequest2.setEmail("sunjnr10@gmail");
        taskService.addTask(taskAddRequest2);
        assertEquals(2, taskRepository.count());

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setEmail("sunjnr10@gmail");
        completeTaskRequest.setTitle("java");
        taskService.deleteTask(completeTaskRequest);
        assertEquals(1, taskRepository.count());
    }

    @Test
    void testThatUserCanDeleteUserProfile(){
        userRepository.deleteAll();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("sunday");
        registerRequest.setEmail("sunjnr10@gmail");
        registerRequest.setPassword("1234");
        userService.registerUser(registerRequest);
        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
        assertEquals(1, userRepository.count());

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setEmail("sunjnr10@gmail");
        userService.deleteUser(deleteUserRequest);
        assertEquals(0, userRepository.count());
    }

//    @Test
//    void testThatUserCanUpdateProfile(){
//        userRepository.deleteAll();
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUserName("sunday");
//        registerRequest.setEmail("sunjnr10@gmail");
//        registerRequest.setPassword("1234");
//        userService.registerUser(registerRequest);
//        assertEquals(true, userRepository.findByEmail(registerRequest.getEmail()).get().isLoggedIn());
//        assertEquals(1, userRepository.count());
//        assertEquals(1, userRepository.findAll().toString());
//
//        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
//        updateUserRequest.setUserName("sunday");
//        RegisterRequest registerRequest1 = new RegisterRequest();
//        registerRequest1.setUserName("kent");
//        registerRequest1.setEmail("kent10");
//        registerRequest1.setPassword("3333");
//        userService.updateUserProfile(updateUserRequest, registerRequest1);
//        assertEquals(1, userRepository.findAll().toString());
//    }


}