package com.tjck.tamblog.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.service.ISchoolService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springAnnotation-*.xml")
@WebAppConfiguration
public class TestTranscation {
	
	@Autowired
	private ApplicationContext ac;

	@Test
	public void testSessionFactory(){
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.err.println(sessionFactory);
	}
	
	public void testTransaction(){
		ISchoolService schoolService = (ISchoolService) ac.getBean("SchoolServiceImpl");
		Tschool school = schoolService.get(1L);
		System.err.println(school.getCityname());
	}
	
}
