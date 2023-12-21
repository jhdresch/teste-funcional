package br.com.testefuncional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FuncionalTest {

	public static WebDriver acessaAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;

	}

	@Test
	public void deveAdicionarTask() {
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
	public void naoDeveAdicionarDatasPassadas() {
		WebDriver driver = acessaAplicacao();
		try {

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
