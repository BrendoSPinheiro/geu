package geu;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GeuWebSteps {
	private static WebDriver driver;
	private static String resultado;
	
	@Given("estou na tela de login")
	public void instanciarLogin() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/geu/");
		Thread.sleep(300);
	}
	
	@When("informo o usuário $user")
	public void informarUsuário(String user) throws InterruptedException {
		WebElement username = driver.findElement(By.id("user"));
		username.sendKeys(user, Keys.TAB);
		Thread.sleep(300);
	}
	
	@When("informo a senha $pass")
	public void informarSenha(String pass) throws InterruptedException {
		WebElement password = driver.findElement(By.id("pass"));
		password.sendKeys(pass, Keys.TAB);
		Thread.sleep(300);
	}
	
	@When("confirmo")
	public void fazerLogin() throws InterruptedException {
		WebElement btnlogin = driver.findElement(By.id("login"));
		btnlogin.click();
		Thread.sleep(300);
	}
	
	@Then("terei o título $titulo")
	public void verificarSituacaoPagina(String titulo) throws InterruptedException {
		WebElement saida = driver.findElement(By.id("titulo"));
		resultado = saida.getText();
		Thread.sleep(300);
		Assert.assertEquals(titulo, resultado);
	}
}
