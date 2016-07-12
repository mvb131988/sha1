package com.crypto.sha1;


public class Main {
	
	private static final long MODULO = 4294967296l; 
	
	public static void main(String[] args) {
		//Initialization step
		//Input data
//		long[] W_00_15 = new long[] {
//				0x48656c6cl,
//				0x6f800000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000000l,
//				0x00000028l
//		};
		
		long[] W_00_15 = new long[] {
				0x48656c6cl,
				0x6f20576fl,
				0x726c6420l,
				0x80000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000000l,
				0x00000060l
		};
		
		long[] W = new long[80];
		for(int i=0; i<80; i++) {
			if(i<16) {
				W[i] = W_00_15[i];
			}
			else{
				W[i] = W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16];
				W[i] = rotateLeft(W[i], 1);
			}
		}
		
		long initA = 0x67452301l;
		long initB = 0xEFCDAB89l;
		long initC = 0x98BADCFEl;
		long initD = 0x10325476l;
		long initE = 0xC3D2E1F0l;
		
		long[] L = new long[80];
		for(int i=0; i<80; i++) {
			if(i>=0 && i<=19) {
				L[i] = 0x5A827999l;
			}
			if(i>19 && i<=39) {
				L[i] = 0x6ED9EBA1l;
			}
			if(i>39 && i<=59) {
				L[i] = 0x8F1BBCDCl;
			}
			if(i>59 && i<=79) {
				L[i] = 0xCA62C1D6l;
			}
		}
		
		long temp = 0;
		long A = initA;
		long B = initB;
		long C = initC;
		long D = initD;
		long E = initE;
		
		//Main step
		for(int i=0; i<80; i++) {
			if(i>=0 && i<=19) {
				temp = plus(plus(plus(plus(rotateLeft(A, 5), f1(B,C,D)), E), W[i]), L[i]);
			}
			if((i>=20 && i<=39) || (i>=60 && i<=79)) {
				temp = plus(plus(plus(plus(rotateLeft(A, 5), f2(B,C,D)), E), W[i]), L[i]);
			}
			if(i>=40 && i<=59) {
				temp = plus(plus(plus(plus(rotateLeft(A, 5), f3(B,C,D)), E), W[i]), L[i]);
			}
			
			E = D;
			D = C;
			C = rotateLeft(B, 30);
			B = A; 
			A = temp;
		}
		
		A = plus(initA, A);
		B = plus(initB, B);
		C = plus(initC, C);
		D = plus(initD, D);
		E = plus(initE, E);
		
		System.out.println(Long.toHexString(A) + " " + Long.toHexString(B) + " " + Long.toHexString(C) + " " + Long.toHexString(D) + " " + Long.toHexString(E));
	}
	
	private static long plus(long a, long b) {
		return (a + b) % MODULO; 
	}
	
	private static long f1(long B, long C, long D) {
		long temp = (B&C)|((~B)&D);
		return temp;
	}
	
	private static long f2(long B, long C, long D) {
		long temp = B^C^D;
		return temp;
	}

	private static long f3(long B, long C, long D) {
		long temp = (B&C)|(B&D)|(C&D);
		return temp;
	}
	
	private static long rotateLeft(long number, long bitsNumber) {
		long result = number;
		for(int i=0; i<bitsNumber; i++) {
			result = rotateLeft(result);
		}
		return result;
	}
	
	private static long rotateLeft(long number) {
		long result = number << 1;
		//33st bit(overflow bit)
		long highestBit = (result & 0x0000000100000000l) >>> 32;
		//remove overflow bit
		result = result & 0x00000000ffffffffl;
		//put overflow bit in 1st bit position
		result += highestBit;
		
		return result;
	}
	
}
