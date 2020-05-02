package com.tensquare.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApA3hToae3BcqgPvVgLUv\n" +
			"zq78Q82xgPivAdN7K7F1bIwMKaP32/b7VCjdGtzT2nKhAKenOmINLK4RuVg5jCWw\n" +
			"xegjGBe0eFpw0e/X+LeQCCdqYlT5Q3xGHLIbxWhkc9rhs5VKekWjUybJTYN+hZ4U\n" +
			"DEm9Pv077HmlaCqvT57VTgSDjcJqdbUWss9JuFMHotoh0/a8ZyLtrs0NPTWO0zdU\n" +
			"S/XgFexuRIye8HNskwCr+9FO75FgRlcaZQtnlmRSBJEr/Zi/7MyZTXQoJzAXjc0t\n" +
			"uQB0JtAXNC7HWk03yoO1KKNMMMkPry89eEkMOwEKHjP7FmuicBXEdybPjJhNW+uL";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEpQIBAAKCAQEApA3hToae3BcqgPvVgLUvzq78Q82xgPivAdN7K7F1bIwMKaP3\n" +
			"2/b7VCjdGtzT2nKhAKenOmINLK4RuVg5jCWwxegjGBe0eFpw0e/X+LeQCCdqYlT5\n" +
			"Q3xGHLIbxWhkc9rhs5VKekWjUybJTYN+hZ4UDEm9Pv077HmlaCqvT57VTgSDjcJq\n" +
			"dbUWss9JuFMHotoh0/a8ZyLtrs0NPTWO0zdUS/XgFexuRIye8HNskwCr+9FO75Fg\n" +
			"RlcaZQtnlmRSBJEr/Zi/7MyZTXQoJzAXjc0tuQB0JtAXNC7HWk03yoO1KKNMMMkP\n" +
			"ry89eEkMOwEKHjP7FmuicBXEdybPjJhNW+uLYwIDAQABAoIBAQCKV/Ih6EBa1WZQ\n" +
			"cw4uhZyVLNcqznDbk9rxLUf3JqUhLlCrZMyFwBddd0BNgN9enb/L75WEFF+LyBbG\n" +
			"N/H/j2Es43+Et6jGvW3ae+miohlh5us9lO7GzCvL0x68MDZVxMUETecKiWMbod+r\n" +
			"8DFnuFCrtBtU3PQFjOfBmg1QlV7HdIlacPcCN6ZLWrAtnFEHmSEGRAcaa0t+va2v\n" +
			"K6+t2zqOwX4dLeTl2Na721zguJtfRGN3NN+99BLqyk8R7rnYz6jRhsn2ym4lqm23\n" +
			"/FL8f/SIbhx8uFAX/eg2c8YD3nGh4D+EkiuzG4CZrOi5u31zNnZ9pd8IFrH7ocnJ\n" +
			"nRN3o8sBAoGBAM9MlVvRby1TfiB7pDWkFA13StkSsk/ZO6bjQye1+/mVlKZdp95/\n" +
			"ohVMOtsL4253x40xU445SMH3KFY9mQ612VrKyv6QZouKovZFaQ7FuSzNIqcZw5aY\n" +
			"Y6m0Ub+Slnrt9OEyscG5HE19/Mrko75C4ZO/pAcdm3mg1aGlzf3KU9LBAoGBAMqY\n" +
			"dA+sfymbMmMljpPeiuV6bsZBpmyFMeIQDHDVpHvJNr6kAbzqTtugj6Y7qxN5m44D\n" +
			"Y3dXLjEnaGsk+tOoDkqvTuubp0K+NcxvpCsVWOD3kHCgaYC1hTThrBKQKFhdTXE0\n" +
			"gGp2JavQnsB32pS+r7cFRValbm8H/kahc84f83sjAoGBAL1mNViMw81f6W74SJIR\n" +
			"9ZhQE/y9DlnSGqRiI+tWZIAm4IgYLdYmOwrGeUfFVuiJW7NvC+53/Dfr/9i7suoz\n" +
			"0AEaI9nuSSLZagckZ9ZtZMDANBcEfSVM2lIlREOKRR7P5tsSnEJpRKiwoZUmzUx6\n" +
			"guuhPtaZgIHiJw3oFYr5nxiBAoGBAK/GeCRFOItDwQLuyfaXxhD2Gfhfa2KKac5G\n" +
			"uFSJuE1CNgJPz8Y10LeTZi8HZBXgk8syO3GGrWguVdVoX0uWatf3pFSdVxB8cbbv\n" +
			"QhOzUMrXYHWnaAIo++r2xdHRS3/na9KFh8lWuraIqcQ5+ObHTzNPGmrj253v5Hkp\n" +
			"iY1UgW7bAoGAYrknaG8VShQsoQnklGecpK/nuQXGukWOhVYL25639QTmBoVsMlC0\n" +
			"ufpEITL+FaBf7kQbCPCwhjRfDNQcDu0PQrMm9+BkpIiILOGtb3fqyWr+dLjY/8XY\n" +
			"fRiR+SN0ehnIskUUdcImv93d4yhW91IGR/fy0VtfmEBL7xW7lEcF310=";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
