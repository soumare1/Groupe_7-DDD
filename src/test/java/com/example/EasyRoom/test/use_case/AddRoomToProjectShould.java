package com.example.EasyRoom.test.use_case;

import com.example.EasyRoom.model.project.ProjectRepository;
import com.example.EasyRoom.model.project.RoomRepository;
import com.example.EasyRoom.model.project.ProjectValidator;
import com.example.EasyRoom.use_case.AddRoomToProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddRoomToProjectShould {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ProjectValidator projectValidator;

    @InjectMocks
    private AddRoomToProject addRoomToProject;

    @Test
    void throwIllegalArgumentExceptionWhenProjectIdIsNull() {
        // No unnecessary stubbing needed here
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addRoomToProject.execute(null, "Room A", 10)
        );
        assertEquals("Project ID cannot be null", exception.getMessage());

        // Verify no interactions occur with mocks
        verify(projectValidator, never()).validateRoomName(anyString());
        verify(projectValidator, never()).validateCapacity(anyInt());
        verify(projectRepository, never()).findById(anyLong());
        verify(roomRepository, never()).save(any());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void throwIllegalArgumentExceptionWhenRoomNameIsInvalid() {
        Long projectId = 1L;
        // Only stub what's necessary for the validation to fail
        doThrow(new IllegalArgumentException("Room name cannot be null or empty"))
                .when(projectValidator).validateRoomName(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addRoomToProject.execute(projectId, null, 10)
        );
        assertEquals("Room name cannot be null or empty", exception.getMessage());

        // Verify interactions stop after validation
        verify(projectValidator).validateRoomName(null);
        verify(projectValidator, never()).validateCapacity(anyInt());
        verify(projectRepository, never()).findById(anyLong());
        verify(roomRepository, never()).save(any());
        verify(projectRepository, never()).save(any());
    }
}