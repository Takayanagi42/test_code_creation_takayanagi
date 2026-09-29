package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		String detailText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(detailText.contains("コース詳細"));

		// 5. Titleの取得とアサーション
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// 6. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加
	}

}
