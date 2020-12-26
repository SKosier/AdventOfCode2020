
public class Day25 {
	public static void main(String[] args) {
		long cardPublicKey = 14788856;
		long doorPublicKey = 19316454;
		
		long loopSizeCard = transform(cardPublicKey, 7);
		long loopSizeDoor = transform(doorPublicKey, 7);
		
		long encryptionKey = transformSubjectKey(cardPublicKey, loopSizeDoor);
		System.out.println(encryptionKey);
	}

	private static long transform(long cardPublicKey, int subjectNumber) {
		long result = 1, loopSize = 0;
		while(result != cardPublicKey) {
			result = result * subjectNumber;
			result = result % 20201227;
			++loopSize;
		}
		return loopSize;
	}
	
	private static long transformSubjectKey(long subjectNumber, long loopSize) {
		long key = 1;
		for(int i = 0; i < loopSize; i++) {
			key = key * subjectNumber;
			key = key % 20201227;
		}
		return key;
	}
}
