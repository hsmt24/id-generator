package example;

import java.util.Date;

import com.fasterxml.uuid.EthernetAddress;

/**
 * IDGenerator.
 * 
 * <pre>
 * RDBのカウントアップではSPOFとなり、スケールが難しいためUUID(v1)形式のIDを生成する
 * 
 * ・時刻ズレ対応（1回前の時刻より古ければException）時刻が未来日に修正された場合は重複の問題はないが、過去日への修正は重複する可能性がある
 * ・同一時刻で最大5000まで採番できる
 * ・最大5000を超える場合はtimestampが新しくなるまで待つ
 * ・IDが時系列でソートできるようにする
 * ・synchronized or ReentrantLockで排他制御
 * http://www.ne.jp/asahi/hishidama/home/tech/java/thread.html
 * http://pgcafe.moo.jp/JAVA/thread/main_5.htm
 * https://examples.javacodegeeks.com/core-java/util/concurrent/locks-concurrent/readwritelock/java-readwritelock-example/
 * http://argius.hatenablog.jp/entry/2015/03/05/120033
 * http://www.ibm.com/developerworks/jp/java/library/j-jtp10264/
 * ・テスト実装
 * ・Macアドレスの圧縮(PForDelta)
 * </pre>
 * 
 * @author hsmt24
 *
 */
public class IDGenerator {

	/** MACアドレス. */
	private static final Long MAC_ADDRESS = EthernetAddress.fromInterface().toLong();
	/** 発行開始シーケンス. */
	private static final Integer START_SEQUENCE = 0;
	/** 最大発行スーケンス. */
	private static final Integer MAX_SEQUENCE = 50000;

	/** 最終ID発行日付. */
	private Long lastTime;

	public static void main(String[] args) throws Exception {
		IDGenerator gen = new IDGenerator();
		for (int i = 0; i < 100; i++) {
			System.out.println("ST" + gen.nextId());
			// Thread.sleep(1000L);
		}
	}

	/**
	 * 独自UUIDを生成.
	 * 
	 * <pre>
	 * code: 8bit
	 * Timestamp:52bit
	 * Mac Address:48bit
	 * Sequence:20bit
	 * </pre>
	 * 
	 * @return
	 */
	public synchronized String nextId() {
		StringBuilder build = new StringBuilder();
		build.append(getTimeStamp());
		build.append("-");
		build.append(getNode());
		build.append("-");
		build.append(nextSequence());
		return build.toString();
	}

	private String getTimeStamp() {
		Date date = new Date();
		// String timestamp = Long.toHexString(date.getTime());
		String timestamp = String.valueOf(date.getTime());
		return timestamp;
	}

	private String getNode() {
		// 16進数変換
		// String node = Long.toHexString(addr.toLong());
		String node = MAC_ADDRESS.toString();
		return node;
	}

	private String nextSequence() {
		String sequence = "00000";
		return sequence;
	}

	public void test() {
		for (int i = 0; i < 100; i++) {
			// Thread.sleep(1000L);
		}
	}
}
