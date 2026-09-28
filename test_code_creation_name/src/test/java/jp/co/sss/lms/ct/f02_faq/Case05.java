package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 1. 指定のURLの画面を開く
		goTo("http://localhost:8080/lms/");

		// 2. Titleの取得とアサーション
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// 3. エビデンスを取得して保存
		getEvidence(new Object() {

		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 1. 初回ログイン済みのログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02");

		// 2. ログインボタンを押下
		webDriver.findElement(By.xpath("//input[@type='submit']")).click();

		// 3. 待機オブジェクトの作成（5秒まで待つ設定）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		// 4. 遷移後の画面に「コース詳細」と表示されていることを検証
		WebElement t2Text = webDriver.findElement(By.tagName("body"));
		String text = t2Text.getText();
		assertTrue(text.contains("コース詳細"));

		// 5. Titleの取得とアサーション
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// 6. エビデンス取得
		getEvidence(new Object() {

		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 1. 上部メニューの機能ボタンを押下
		webDriver.findElement(By.linkText("機能")).click();

		// 2. プルダウン内のヘルプを押下
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 3. 遷移後の画面に「ヘルプ」と表示されていることを検証
		String helpIndexText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(helpIndexText.contains("ヘルプ"));

		// 4. エビデンス取得
		getEvidence(new Object() {

		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 1.現在のタブを取得
		String currentWindow = webDriver.getWindowHandle();

		// 2. クリック前のタブ数
		int tabBefore = webDriver.getWindowHandles().size();

		// 3. 画面内の「よくある質問」を押下
		webDriver.findElement(By.linkText("よくある質問")).click();

		// 4. クリック後のタブ数
		int tabAfter = webDriver.getWindowHandles().size();

		// 5. 別タブが開いたことの確認
		assertEquals(tabBefore + 1, tabAfter);

		// 6.別タブへ切り替える
		for (String window : webDriver.getWindowHandles()) {
			if (!window.equals(currentWindow)) {
				webDriver.switchTo().window(window);
				break;
			}
		}

		// 7. 遷移後の画面に「よくある質問」と表示されていることを検証
		String faqIndexText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(faqIndexText.contains("よくある質問"));

		// 8. エビデンス取得
		getEvidence(new Object() {

		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 1.キーワードを入力
		WebElement searchBox = webDriver.findElement(By.id("form"));
		searchBox.sendKeys("キャンセル");

		// 2.検索ボタンをクリック
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		// 3. 待機オブジェクトの作成（5秒まで待つ設定）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		// 4. 検索結果を取得
		List<WebElement> results = wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.cssSelector("tbody tr")));

		// 5.すべての検索結果に「キャンセル」が含まれていることを確認
		for (WebElement result : results) {
			String text = result.getText();

			assertTrue(
					text.contains("キャンセル"),
					"検索結果にキーワード「キャンセル」が含まれていません: " + text);
		}

		// 6.検索結果までスクロール
		WebElement firstResult = results.get(0);

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				firstResult);

		// 7. エビデンス取得
		getEvidence(new Object() {

		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 1.「クリア」ボタンを押下
		webDriver.findElement(
				By.cssSelector("input[type='button'][value='クリア']")).click();

		// 2.キーワード入力欄を取得
		WebElement searchBox = webDriver.findElement(By.id("form"));

		// 3.入力欄が空になっていることを確認
		assertEquals("", searchBox.getAttribute("value"));

		// 4.エビデンス取得
		getEvidence(new Object() {
		});
	}

}
