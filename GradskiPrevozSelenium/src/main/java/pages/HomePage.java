package pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage  {



	String APP_TITLE = "GadskiFront";
	String APP_URL = "http://localhost:4200/";

	@FindBy(xpath = "//*[@id='mat-input-0']")
	private WebElement usernameField;
	@FindBy(xpath = "//*[@id='mat-input-1']")
	private WebElement passwordField;

	@FindBy(xpath = "/html/body/app-root/my-nav/mat-sidenav-container/mat-sidenav-content/mat-toolbar/app-login-small/form/button")
	private WebElement loginBtn;
	@FindBy(xpath = "/html/body/app-root/my-nav/mat-sidenav-container/mat-sidenav-content/mat-toolbar/button")
	private WebElement menuBtn;
	
	@FindBy(xpath = "/html/body/app-root/my-nav/mat-sidenav-container/mat-sidenav/div/mat-nav-list/a[1]/div")
	private WebElement pricelistBtn;
	
	@FindBy(xpath = "/html/body/app-root/my-nav/mat-sidenav-container/mat-sidenav-content/div[3]/app-pricelist-detail/div/div/table/tbody/tr[1]/td[5]/button[2]")
	private WebElement buyBtn;
	
	@FindBy(xpath = "//*[@id='yes-button']")
	private WebElement confirmBtn;
	
	@FindBy(xpath = "//span[text()='Uspešno ste kupili mesecna kartu.']")
	private WebElement successNotification;
	// WebDriver instance.
	private WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(WebElement usernameField) {
		this.usernameField = usernameField;
	}

	public WebElement getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(WebElement passwordField) {
		this.passwordField = passwordField;
	}
	
	public WebElement getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(WebElement loginBtn) {
		this.loginBtn = loginBtn;
	}
	
	public WebElement getMenuBtn() {
		return menuBtn;
	}

	public void setMenuBtn(WebElement menuBtn) {
		this.menuBtn = menuBtn;
	}
	
	public WebElement getPricelistBtn() {
		return pricelistBtn;
	}


	public void setPricelistBtn(WebElement pricelistBtn) {
		this.pricelistBtn = pricelistBtn;
	}
	

	public WebElement getBuyBtn() {
		return buyBtn;
	}

	public void setBuyBtn(WebElement buyBtn) {
		this.buyBtn = buyBtn;
	}
	
	public WebElement getConfirmBtn() {
		return confirmBtn;
	}

	public void setConfirmBtn(WebElement confirmBtn) {
		this.confirmBtn = confirmBtn;
	}
	

	public WebElement getSuccessNotification() {
		return successNotification;
	}

	public void setSuccessNotification(WebElement successNotification) {
		this.successNotification = successNotification;
	}
	public void ensureSuccessNotificationIsVisible(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(successNotification));

	}
	public void ensureConfirmBtnIsClickable(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(confirmBtn));
	}
	public void ensureBuyBtnIsClickable(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(buyBtn));
	}
	
	public void ensurePricelistBtnIsClickable(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(pricelistBtn));

	}
	public void ensureMenuBtnIsClickable(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(menuBtn));

	}
	public void ensureLoginBtnIsClickable(){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginBtn));

	}
	public void ensureUsernameFieldIsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(usernameField));
	}
	public void ensurePasswordFieldIsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(passwordField));
	}
	
	public String getAPP_URL() {
		return APP_URL;
	}



	public void setAPP_URL(String aPP_URL) {
		APP_URL = aPP_URL;
	}

	

}
