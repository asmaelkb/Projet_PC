package prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {
	
	// On charge la valeur des constantes
	
	static int nProd;
	static int nCons;
	static int bufSz;
	static int prodTime;
	static int consTime;
	static int minProd;
	static int maxProd;
	
	static Producer[] prod;
	static Consumer[] cons;
	
	static ProdConsBuffer buff;
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		
		Properties properties = new Properties();
		
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		
		nProd = Integer.parseInt(properties.getProperty("nProd"));
		nCons = Integer.parseInt(properties.getProperty("nCons"));
		bufSz = Integer.parseInt(properties.getProperty("bufSz"));
		prodTime = Integer.parseInt(properties.getProperty("prodTime"));
		consTime = Integer.parseInt(properties.getProperty("consTime"));
		minProd = Integer.parseInt(properties.getProperty("minProd"));
		maxProd = Integer.parseInt(properties.getProperty("maxProd"));
		
		prod = new Producer[nProd];
		cons = new Consumer[nCons];
		
		buff = new ProdConsBuffer(bufSz);
		
		// Threads producteurs 
		for(int i = 0; i < nProd; i++) {
			prod[i] = new Producer(buff, prodTime, minProd, maxProd);
			prod[i].start();
		}
		
		// Threads consommateurs
		for(int i = 0; i < nCons; i++) {
			cons[i] = new Consumer(buff, consTime);
			cons[i].start();
		}
			
	}
}