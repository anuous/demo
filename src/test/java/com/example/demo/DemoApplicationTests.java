package com.example.demo;

import com.example.demo.daoImpl.UserMapper;
import com.example.demo.model.Dept;
import com.example.demo.model.User;
import com.example.demo.rabbitmq.Sender;
import com.example.demo.scheduled.SyncServiceImpl;
import com.example.demo.serviceImpl.service.IUserService;
import com.github.pagehelper.PageInfo;
import freemarker.ext.beans.HashAdapter;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	@Autowired
	public UserMapper userMapper;

	@Autowired
	public IUserService userService;

	@Autowired
	public Sender sender;

	@Autowired
	public JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;  //自动注入

	@Autowired
	private SyncServiceImpl syncService;
	@Test
	public void testInsert(){
		//Assert.assertEquals(1L,userMapper.insert("anuous",21).longValue());
		User user=new User();
		user.setName("testName_2");
		user.setAge(18);
		Long id=userService.addUser(user);
		System.out.println(user.toString());
	}
	@Test
	public void testGetByName(){
		String name="testName_2";
		User user=userService.getByName(name);
		System.out.println(user.toString());
	}

	@Test
	public void testInsertDept(){
		Dept dept=new Dept();
		dept.setDeptName("Human Resource");
		dept.setDeptNo("001");
		Long res=userService.addDept(dept);
		System.out.println(res);
	}


	@Test
	public void testPage(){
		PageInfo<User> users=userService.listUser(2,1);
		System.out.println(users.getSize());
	}

	@Test
	public void testMQ(){
		sender.sender("this is a test sender message !");
	}

	@Test
	public void testSimpleMail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("576286447@qq.com");
		message.setTo("yuanguang.luo@iqunxing.com");
		message.setSubject("主题：测试邮件");
		message.setText("this is a test mail !");
		javaMailSender.send(message);
	}

	//测试附件发送 文本模板化发送
	@Test
	public void sendMailWithFramework() throws Exception{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("576286447@qq.com");
		helper.setTo("yuanguang.luo@iqunxing.com");
		helper.setSubject("主题：有附件-模板邮件");
		helper.setText("有附件的模板 邮件");

		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\ANUOUS\\Desktop\\新建文件夹\\file.png"));
		helper.addAttachment("附件-1.jpg", file);
		Map<String ,Object> model=new HashMap<String,Object>();
		model.put("username","anuous");
		model.put("title","模板发送 测试！");
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("index.html");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		helper.setText(html, true);
		helper.addInline("img",file);

		javaMailSender.send(message);
	}
	//测试附件发送 文本模板化发送
	@Test
	public void sendMailWithAttachment() throws Exception{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("576286447@qq.com");
		helper.setTo("yuanguang.luo@iqunxing.com");
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");

		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\ANUOUS\\Desktop\\新建文件夹\\file.png"));
		helper.addAttachment("附件-1.jpg", file);
		helper.addAttachment("附件-2.jpg", file);

		IContext context = new Context();
		context.getVariables();
		//获取模板html代码
		String process = templateEngine.process("index", context);
		helper.setText(process, true);
		helper.addInline("img",file);
		javaMailSender.send(message);
	}

	@Test
	public void TestSync() throws Exception{
		long start=System.currentTimeMillis();
		Future<String> one= syncService.syncInputOne();
		Future<String> two=syncService.syncInputTwo();
		Future<String> three=syncService.syncInputThree();
		while(true){
			if(one.isDone()&&two.isDone()&&three.isDone()){
				System.out.println("全部任务完成！耗时："+(System.currentTimeMillis()-start)+" ms");
				break;
			}
			Thread.sleep(100);
		}
		//Thread.sleep(50000);
	}



}
