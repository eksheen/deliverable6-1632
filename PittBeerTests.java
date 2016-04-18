//package com.example.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class PittBeerTests {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://pittbeer.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //Given that I am at the home page
    //When I see the title
    //Then I see that I am at Beer Home
    @Test
    public void testTitle() throws Exception {
        driver.get(baseUrl + "/home");
        String title = driver.getTitle();
        assertTrue(title.contains("Beer Home"));
    }

    /*********************************************
     *               User Stories                *
     *********************************************/

    //Given that I am on the vieworders page
    //Then I see my orders
    @Test
    public void testViewOrders() throws Exception {
        signIn();
        driver.get(baseUrl + "/vieworders");
        assertEquals("My Orders", driver.findElement(By.cssSelector("h2")).getText());
        signOut();
    }

    //Given that I am on the loadfunds page
    //When I add funds
    //Then I see the account page
    @Test
    public void testAddFunds() throws Exception {
        signIn();
        driver.get(baseUrl + "/loadfunds");
        driver.findElement(By.name("amount")).clear();
        driver.findElement(By.name("amount")).sendKeys("5");
        driver.findElement(By.xpath("//input[@value='Proceed to payment']")).click();
        String title = driver.getTitle();
        assertTrue(title.contains("Account")); //I use this assertTrue because you always end up at the account page after a successful loading of funds
        signOut();
    }



    //Given that I am on the beer page
    //When I see click sort by price
    //Then I see that the lowest price beers are first in the list
    @Test
    public void sortBeerByPrice() {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.xpath("//table[@id='tblBeers']/thead/tr/th[5]")).click();
        assertEquals("$3.00", driver.findElement(By.id("price-36")).getText());
    }

    //Given that I am on the beer page
    //When I see click sort by style
    //Then I see that the styles in alphabetical order
    @Test
    public void testSortStyle() throws Exception {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.xpath("//table[@id='tblBeers']/thead/tr/th[3]")).click();
        assertEquals("Amber Ale", driver.findElement(By.cssSelector("td.sorting_1")).getText());
        assertEquals("American Amber", driver.findElement(By.cssSelector("tr.even > td.sorting_1")).getText());
        assertEquals("American Brown Ale", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[5]/td[3]")).getText());
    }

    //Given that I am on the beer page
    //When I see click sort by product
    //Then I see that the names of the beer in alphabetical order
    @Test
    public void testSortName() throws Exception {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.xpath("//table[@id='tblBeers']/thead/tr/th[2]")).click();
        assertEquals("2X Stout (Nitro)", driver.findElement(By.cssSelector("td.sorting_1")).getText());
        assertEquals("312 Urban Wheat", driver.findElement(By.cssSelector("tr.even > td.sorting_1")).getText());
        assertEquals("American Amber (Special Dark)", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[3]/td[2]")).getText());
        assertEquals("American Light Lager", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[4]/td[2]")).getText());
    }

    //Given that I am on the beer page
    //When I see click sort by ABV
    //Then I see the ABV's of the beers in numerical order from lowest to highest
    @Test
    public void testSortABV() throws Exception {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.xpath("//table[@id='tblBeers']/thead/tr/th[4]")).click();
        assertEquals(driver.findElement(By.cssSelector("td.sorting_1")).getText(), "3.16%");
        assertEquals(driver.findElement(By.cssSelector("tr.even > td.sorting_1")).getText(), "3.5%");
        assertEquals(driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[3]/td[4]")).getText(), "3.6%");
    }

    //Given that I am on the beer page
    //When I see click sort by brewery
    //Then I see that the names of the breweries in alphabetical order
    @Test
    public void testSortByBrewery() throws Exception {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.cssSelector("th.sorting_asc")).click();
        assertEquals("Yuengling", driver.findElement(By.cssSelector("td.sorting_1")).getText());
        assertEquals("Weltenburger", driver.findElement(By.cssSelector("tr.even > td.sorting_1")).getText());
        assertEquals("Wells & Young", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[3]/td")).getText());
        driver.findElement(By.cssSelector("th.sorting_desc")).click();
        assertEquals("Allagash Brewing Company", driver.findElement(By.cssSelector("td.sorting_1")).getText());
        assertEquals("Alltech", driver.findElement(By.cssSelector("tr.even > td.sorting_1")).getText());
        assertEquals("Anchor Brewing", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr[3]/td")).getText());
    }

    /********************************************
    *            Stakeholder Stories            *
    *********************************************/

    //Given that I am on the home page
    //When I click account
    //Then I can sign in
    @Test
    public void SignIn() throws Exception {
        driver.get(baseUrl + "/account");
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("cs1632testacc@gmail.com");
        driver.findElement(By.id("next")).click();
        driver.findElement(By.id("Passwd")).clear();
        driver.findElement(By.id("Passwd")).sendKeys("1632testacc");
        driver.findElement(By.id("signIn")).click();
        driver.findElement(By.id("approve_button")).click();
    }


    //Given that I am on the beer page
    //When I type in a beer name
    //Then I see the name of the beer I typed in
    @Test
    public void testSearchBeer() throws Exception {
        driver.get(baseUrl + "/beer");
        driver.findElement(By.cssSelector("input.form-control.input-sm")).clear();
        driver.findElement(By.cssSelector("input.form-control.input-sm")).sendKeys("Magic Hat #9");
        assertEquals("Magic Hat #9", driver.findElement(By.xpath("//table[@id='tblBeers']/tbody/tr/td[2]")).getText());
    }

    //Given that I am navigating to the site for the first time
    //Then I see a page that verfies I am over 21
    @Test
    public void testOver21() throws Exception {
        driver.manage().deleteCookieNamed("age-verified");
        driver.get(baseUrl + "/");
        String ofAge = driver.findElement(By.cssSelector("p")).getText();
        assertEquals("Are you 21 years of age?", ofAge);
        driver.findElement(By.cssSelector("button")).click();
    }

    //Given that I am on the order page
    //When I see type in an address
    //Then I see if that address is within the 5 mile limit
    @Test
    public void test5mile() throws Exception {
        signIn();
        driver.get(baseUrl + "/order");
        driver.findElement(By.id("addressTxt")).clear();
        driver.findElement(By.id("addressTxt")).sendKeys("3990 Fifth Ave, Pittsburgh, PA 15213");
        driver.findElement(By.id("addressTxt")).sendKeys(Keys.RETURN);
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("3990 Fifth Ave, Pittsburgh, PA 15213, USA\n0.2 mi, 1 min\nHey great news... WE DELIVER BEER TO YOU!!!".equals(driver.findElement(By.id("addressResult")).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
        assertEquals(driver.findElement(By.id("addressResult")).getText(), "3990 Fifth Ave, Pittsburgh, PA 15213, USA\n0.2 mi, 1 min\nHey great news... WE DELIVER BEER TO YOU!!!");
        driver.findElement(By.id("addressTxt")).clear();
        driver.findElement(By.id("addressTxt")).sendKeys("1700 West St, Homestead, PA 15120");
        driver.findElement(By.id("addressTxt")).sendKeys(Keys.RETURN);
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("1700 West St, Homestead, PA 15120, USA\n5.4 mi, 17 mins\nSorry to say this, but you are outside of our delivery range :(".equals(driver.findElement(By.id("addressResult")).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
        assertEquals(driver.findElement(By.id("addressResult")).getText(), "1700 West St, Homestead, PA 15120, USA\n5.4 mi, 17 mins\nSorry to say this, but you are outside of our delivery range :(");
        signOut();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    //Method to signIn when I need to
    public void signIn() {
        driver.get(baseUrl + "/account");
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("cs1632testacc@gmail.com");
        driver.findElement(By.id("next")).click();
        driver.findElement(By.id("Passwd")).clear();
        driver.findElement(By.id("Passwd")).sendKeys("1632testacc");
        driver.findElement(By.id("signIn")).click();
        driver.findElement(By.id("approve_button")).click();
    }

    //Method to signOut when I need to
    public void signOut() {
        driver.get("http://pittbeer.com/_ah/logout?continue=https://www.google.com/accounts/Logout%3Fcontinue%3Dhttps://appengine.google.com/_ah/logout%253Fcontinue%253Dhttp://pittbeer.com/home%26service%3Dah");
    }
}
