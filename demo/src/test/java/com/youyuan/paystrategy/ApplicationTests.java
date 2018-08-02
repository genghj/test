package com.youyuan.paystrategy;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private JavaMailSender mailSender;
	@Test
	public void contextLoads() {
	}
	/**
	 * 修改application.properties的用户，才能发送。
	 */
	@Test
	public void sendSimpleEmail(){
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("8588gz@163.com");//发送者.
		message.setTo("8588gz@163.com");//接收者.
		message.setSubject("测试邮件（邮件主题）");//邮件主题.
		message.setText("这是邮件内容");//邮件内容.

		mailSender.send(message);//发送邮件
	}

	/**
	 * 测试发送附件.(这里发送图片.)
	 * @throws MessagingException
	 */
	@Test
	public void sendAttachmentsEmail() throws MessagingException {
		//这个是javax.mail.internet.MimeMessage下的，不要搞错了。
		MimeMessage mimeMessage =  mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		//基本设置.
		helper.setFrom("8588gz@163.com");//发送者.
		helper.setTo("8588gz@163.com");//接收者.
		helper.setSubject("测试附件（邮件主题）");//邮件主题.
		helper.setText("这是邮件内容（有附件哦.）");//邮件内容.

		//org.springframework.core.io.FileSystemResource下的:
		//附件1,获取文件对象.
		FileSystemResource file1 = new FileSystemResource(new File("D:/test/head/head1.jpg"));
		//添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了。
		helper.addAttachment("头像1.jpg", file1);
		//附件2
		FileSystemResource file2 = new FileSystemResource(new File("D:/test/head/head2.jpg"));
		helper.addAttachment("头像2.jpg", file2);

		mailSender.send(mimeMessage);
	}

	/**
	 * 邮件中使用静态资源.
	 * @throws Exception
	 */
	@Test
	public void sendInlineMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//基本设置.
		helper.setFrom("8588gz@163.com");//发送者.
		helper.setTo("8588gz@163.com");//接收者.
		helper.setSubject("测试静态资源（邮件主题）");//邮件主题.
		// 邮件内容，第二个参数指定发送的是HTML格式
		//说明：嵌入图片<img src='cid:head'/>，其中cid:是固定的写法，而aaa是一个contentId。
		helper.setText("<body>这是图片：<img src='cid:head' /></body>", true);

		FileSystemResource file = new FileSystemResource(new File("D:/test/head/head1.jpg"));
		helper.addInline("head",file);

		mailSender.send(mimeMessage);

	}

	/**
	 * 模板邮件；
	 * @throws Exception
	 */
	@Test
	public void sendTemplateMail() throws Exception {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//基本设置.
		helper.setFrom("8588gz@163.com");//发送者.
		helper.setTo("8588gz@163.com");//接收者.
		helper.setSubject("模板邮件（邮件主题）");//邮件主题.

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", "林峰1");

//		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
//		// 设定去哪里读取相应的ftl模板
//		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
//		// 在模板文件目录中寻找名称为name的模板文件
//		Template template   = cfg.getTemplate("email.ftl");
//
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//		helper.setText(html, true);

		mailSender.send(mimeMessage);
	}
	/**
	 * Junit基本注解介绍
	 //在所有测试方法前执行一次，一般在其中写上整体初始化的代码
	 @BeforeClass


	 //在所有测试方法后执行一次，一般在其中写上销毁和释放资源的代码
	 @AfterClass


	 //在每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据）
	 @Before


	 //在每个测试方法后执行，在方法执行完成后要做的事情
	 @After


	 // 测试方法执行超过1000毫秒后算超时，测试将失败
	 @Test(timeout = 1000)


	 // 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败
	 @Test(expected = Exception.class)


	 // 执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类
	 @Ignore(“not ready yet”)


	 @Test


	 @RunWith

	 在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，你要根据需要选择不同的Runner来运行你的测试代码。

	 如果我们只是简单的做普通Java测试，不涉及Spring Web项目，你可以省略@RunWith注解，这样系统会自动使用默认Runner来运行你的代码。
	 */
}
