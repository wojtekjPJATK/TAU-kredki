import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import utils


class MyStoreTest(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome(executable_path=r"C:\Users\Wojtek\Documents\workspace\TAU\selenium\chromedriver.exe")
        self.driver.set_window_size(1920, 1080)

    def test_contact_tab_url(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        url = driver.current_url
        contact_btn = driver.find_element(By.CSS_SELECTOR, '#contact-link > a')
        contact_btn.click()
        self.assertNotEqual(url, driver.current_url)

    def test_title_in_main_page(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        self.assertIn("Store", driver.title)
        self.assertNotIn("No results found.", driver.page_source)

    def test_login_with_invalid_email_format(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        sign_in_btn = driver.find_element(By.XPATH, '//*[@id="header"]/div[2]/div/div/nav/div[1]/a')
        sign_in_btn.click()
        email_input = driver.find_element(By.XPATH, '//*[@id="email"]')
        email_input.send_keys("Not an email")
        password_input = driver.find_element(By.ID, 'passwd')
        password_input.send_keys("secret123")
        submit_btn = driver.find_element(By.ID, 'SubmitLogin')
        submit_btn.click()
        self.assertIn("Invalid email address.", driver.page_source)
        driver.save_screenshot("not_email_login.png")

    def test_login_with_invalid_credentials(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        sign_in_btn = driver.find_element(By.XPATH, '//*[@id="header"]/div[2]/div/div/nav/div[1]/a')
        sign_in_btn.click()
        email_input = driver.find_element(By.XPATH, '//*[@id="email"]')
        email_input.send_keys("email@example.com")
        password_input = driver.find_element(By.ID, 'passwd')
        password_input.send_keys("secret123")
        submit_btn = driver.find_element(By.ID, 'SubmitLogin')
        submit_btn.click()
        self.assertIn("Authentication failed.", driver.page_source)
    
    def test_login_with_valid_credentials_on_mobile_width(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        driver.set_window_size(375, 812)
        sign_in_btn = driver.find_element(By.XPATH, '//*[@id="header"]/div[2]/div/div/nav/div[1]/a')
        sign_in_btn.click()
        email_input = driver.find_element(By.XPATH, '//*[@id="email"]')
        email_input.send_keys("adamnowak@wp.pl")
        password_input = driver.find_element(By.ID, 'passwd')
        password_input.send_keys("secret1")
        submit_btn = driver.find_element(By.ID, 'SubmitLogin')
        submit_btn.click()
        nav_page = driver.find_element(By.CLASS_NAME, 'navigation_page')
        self.assertIn("My account", nav_page.text)
        driver.save_screenshot("mobile_login.png")

    def test_create_account_with_existing_email(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        sign_in_btn = driver.find_element(By.XPATH, '//*[@id="header"]/div[2]/div/div/nav/div[1]/a')
        sign_in_btn.click()
        email_input = driver.find_element(By.ID, 'email_create')
        email_input.send_keys("adamnowak@wp.pl")
        submit_btn = driver.find_element(By.ID, 'SubmitCreate')
        submit_btn.click()
        try:
            element = WebDriverWait(driver, 10).until(EC.text_to_be_present_in_element((By.ID, 'create_account_error'), 'email'))
        except TimeoutException:
            self.fail("Error message not found in 10 seconds")
        error = driver.find_element(By.ID, 'create_account_error')
        self.assertIn("email address has already been registered", error.text)

    def test_create_account_with_valid_email(self):
        driver = self.driver
        driver.get("http://automationpractice.com/index.php")
        sign_in_btn = driver.find_element(By.XPATH, '//*[@id="header"]/div[2]/div/div/nav/div[1]/a')
        sign_in_btn.click()
        email_input = driver.find_element(By.ID, 'email_create')
        email_input.send_keys(utils.generate_email())
        submit_btn = driver.find_element(By.ID, 'SubmitCreate')
        submit_btn.click()
        try:
            element = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, 'submitAccount')))
        except TimeoutException:
            self.fail("Register button not found in 10 seconds")
        register_btn = driver.find_element(By.ID, 'submitAccount')
        self.assertTrue(register_btn.is_displayed())


    def tearDown(self):
        self.driver.close()

if __name__ == "__main__":
    unittest.main()
