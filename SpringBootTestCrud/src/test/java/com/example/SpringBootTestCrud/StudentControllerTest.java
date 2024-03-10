package com.example.SpringBootTestCrud;

import com.example.SpringBootTestCrud.controllers.StudentController;
import com.example.SpringBootTestCrud.entities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;





@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class StudentControllerTest {

	@Autowired
	private StudentController studentController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private Student student() {
		return new Student();
	}

	private String studentJson(Student student) throws JsonProcessingException {
		return objectMapper.writeValueAsString(student);
	}

	private MvcResult addStudent(Student student) throws Exception {
		return this.mockMvc.perform(post("/student/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(studentJson(student)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	private Student returnStudentFromResp(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
		return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class);
	}

	@Test
	void createStudent() throws Exception {
		MvcResult result = addStudent(student());
		Student studentResponse = returnStudentFromResp(result);
		assertThat(studentResponse.getId()).isGreaterThan(0);
	}

	@Test
	void readAllStudent() throws Exception {
		addStudent(student());
		addStudent(student());
		addStudent(student());
		MvcResult result = this.mockMvc.perform(get("/student/getall"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		List<Student> listResponde = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
		assertThat(listResponde.size()).isGreaterThan(0);
	}

	@Test
	void readStudentById() throws Exception {

		Student student = returnStudentFromResp(addStudent(student()));
		MvcResult result = this.mockMvc.perform(get("/student/getById/" + student.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		Student student1 = returnStudentFromResp(result);
		assertThat(student.getId()).isEqualTo(student1.getId());
	}

	@Test
	void updateStudentById() throws Exception {

		Student student = returnStudentFromResp(addStudent(student()));
		Student studentUp = student();
		studentUp.setName("maxo");
		MvcResult result = this.mockMvc.perform(put("/student/update/" + student.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(studentJson(studentUp)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		Student studentResp = returnStudentFromResp(result);
		assertThat(student.getId()).isEqualTo(studentResp.getId());
		assertThat(student.getName()).isNotEqualTo(studentResp.getName());
	}

	@Test
	void updateStatusById() throws Exception {
		Student student = returnStudentFromResp(addStudent(student()));
		MvcResult result = this.mockMvc.perform(put("/student/statusupdate/" + student.getId())
				.param("working", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		Student studentResp = returnStudentFromResp(result);
		assertThat(studentResp.isWorking()).isEqualTo(true);
	}

	@Test
	void deleteById() throws Exception {
		Student student = returnStudentFromResp(addStudent(student()));
		this.mockMvc.perform(delete("/student/delete/" + student.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		assertThat(studentController.getById(student.getId())).isNotPresent();
	}
}