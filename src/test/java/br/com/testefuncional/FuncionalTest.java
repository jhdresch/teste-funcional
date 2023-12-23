package br.com.testefuncional;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FuncionalTest {

	public static WebDriver acessaAplicacao() throws MalformedURLException {
		//WebDriver driver = new ChromeDriver(); comunicação local
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.17.0.1:4444/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;

	}

	@Test
	public void deveAdicionarTask() throws MalformedURLException {
		WebDriver driver = acessaAplicacao();

		try {
			driver.navigate().to("http://localhost:8001/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// clikar no botao
			driver.findElement(By.className("btn-outline-secondary")).click();

			// escrever DESCRICAO
			driver.findElement(By.className("form-control")).sendKeys("testeeeeee");
			;

			// escrever DATA
			driver.findElement(By.id("dueDate")).sendKeys("30/12/2030");
			;

			// clikar no SALVAR
			driver.findElement(By.id("saveButton")).click();

			// clikar VER MENSAGEM DE SUCESSO

			String text = driver.findElement(By.className("alert-success")).getText();

			Assert.assertEquals("Sucesso!", text);
		} finally {
			// fechar o navegador
			driver.quit();
		}

	}

	@Test
	public void naoDeveAdicionarDatasPassadas() throws MalformedURLException {
		WebDriver driver = acessaAplicacao();
		try {

			driver.navigate().to("http://localhost:8001/tasks/");
			// clikar no botao
			driver.findElement(By.className("btn-outline-secondary")).click();

			// escrever DESCRICAO
			driver.findElement(By.className("form-control")).sendKeys("testeeeeee");

			// escrever DATA
			driver.findElement(By.id("dueDate")).sendKeys("30/12/2001");

			// clikar no SALVAR
			driver.findElement(By.id("saveButton")).click();

			// clikar VER MENSAGEM DE SUCESSO

			String text = driver.findElement(By.className("alert-danger")).getText();

			Assert.assertEquals("Due date must not be in past", text);
			
		} finally {
			// fechar o navegador
			driver.quit();
		}

	}

}
