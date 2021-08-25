package cs284hw;

/**
 * @author Benjamin Griepp
 * 
 * Grade: 45/40
 * 
 * Assignment: Write methods with the Time Complexity specified (view pdf for complexities)
 * 
 * I pledge my honor I have abided by the Stevens Honor System
 */
public class Complexity {
	
//1.
	public static void method1(int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.println("Iteration" + i + j);
			}
		}
	}
	
//2.
	public static void method2(int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					System.out.println("Iteration " + i + j + k);
				}
			}
		}
	}
	
//3.
	public static void method3(int n) {
		for (int i = 1; i < n; i = i * 2){
		    System.out.println("Iteration " + i);
		}
	}

//4.
//	+---+----+----+
//	| i | s  | e  |
//	+---+----+----+
//	| 1 | 0  | 31 |
//	+---+----+----+
//	| 2 | 16 | 31 |
//	+---+----+----+
//	| 3 | 24 | 31 |
//	+---+----+----+
//	| 4 | 28 | 31 |
//	+---+----+----+
//	| 5 | 30 | 31 |
//	+---+----+----+
//	| 6 | 31 | 31 |
//	+---+----+----+

//	+---+----+----+
//	| i | s  | e  |
//	+---+----+----+
//	| 1 | 0  | 63 |
//	+---+----+----+
//	| 2 | 32 | 63 |
//	+---+----+----+
//	| 3 | 48 | 63 |
//	+---+----+----+
//	| 4 | 56 | 63 |
//	+---+----+----+
//	| 5 | 60 | 63 |
//	+---+----+----+
//	| 6 | 62 | 63 |
//	+---+----+----+
//	| 7 | 63 | 63 |
//	+---+----+----+
	
//5. The number of iterations = log(size, 2) + 1
	
//6. O(log n)
	
//7.
	public static boolean bSearch ( int [] a , int x) {
		int end = a. length - 1;
		int start = 0;
		while ( start <= end ) {
		System.out.println("Start: " + start + " End: " + end);
		int mid = ( end - start ) / 2 + start ;
		if (a[ mid ] == x) return true ;
		else if (a[ mid ] > x) end = mid - 1;
		else start = mid + 1;
		}
		return false;
	}

//8.
	public static void method4(int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 1; j < n; j*=2) {
                System.out.println("Iteration " + i + j);
            }
        }
    }
	
//9.
	public static void method5(int n) {
        for(double i = n; i > 2; i = Math.sqrt(i)) {
                System.out.println("Iteration " + (int) i);
        }
    }
	
//10.
	public static void method6(int n) {
		System.out.println("Iteration:" + n);
		if(n > 1) {
			method6(n-1);
			method6(n-1);
		}
	}

}
