package br.ce.rafael.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {


    public WebDriver acessarAplicacao() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "/mnt/40C87AD620054E7D/Projetos/Drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("--js-flags=--expose-gc");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("test-type=browser");
        options.addArguments("disable-infobars");
        options.addArguments("incognito");

//        WebDriver driver = new ChromeDriver(options);
//        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://172.17.0.1:4444/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.navigate().to("http://192.168.0.9:8001/tasks");
        return driver;
    }


    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso.
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        }finally {
            driver.quit();
        }
    }


    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso.
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naodeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {

            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso.
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/1990");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso.
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        }finally {
            driver.quit();
        }
    }

}
