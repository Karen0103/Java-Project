package testExample;

import java.util.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/*
 * this class is used to display the life expectancy of countries all over the world as data points on a map
 */
public class My_LifeExpectancy extends PApplet {
	
	UnfoldingMap map;
	HashMap<String,Float> LifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers; //Abstract marker handling conversion of location to appropriate coordinate system
	
	public void setup(){
		size(800,600,OPENGL);
		map = new UnfoldingMap(this,50,50,700,500,new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this,map);
		LifeExpByCountry = LifeExpByCountryFromCSV("LifeExpectancyWorldBankModule3.csv");
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	
	

	public void draw(){
		//each UnfoldingMap object call its own draw() method that will refresh the screen
		//on the current objects and parameters associated with the map.
		map.draw();
	}
	
	private HashMap<String, Float> LifeExpByCountryFromCSV(String filename) {
		// read data from csv file and return a Map<country, data>
		HashMap<String, Float> LifeExpMap = new HashMap<String, Float>() ;
		String[] rows = loadStrings(filename);
		
		for(String row : rows){
			//In csv file, each row is data of one country
			//we use country code which is fourth attribute as key and fifth attribute as value
			String[] columns = row.split(",");
			if(columns.length == 6 && !columns[5].equals("..")){
				Float value = Float.parseFloat(columns[5]);
				LifeExpMap.put(columns[4], value);
			}
		}
		return LifeExpMap;
	}
	
	private void shadeCountries() {
		// set different color to country markers according to the life expectancy
		for(Marker marker:countryMarkers){
			String countryID = marker.getId();
			if(LifeExpByCountry.containsKey(countryID)){
				float lifeExp = LifeExpByCountry.get(countryID);
				//PApplet: map() convert one range to another range, (40,90) --> (10,255)
				int colorLevel = (int)map(lifeExp,40,90,10,255); 
				marker.setColor(color(255-colorLevel,100,colorLevel));
			}
			else{
				marker.setColor(color(150,150,150));
			}
		}
	}
	
	

}
