import java.sql.ResultSet;



public class SingleWord {
	
	String type, word, meaning;
	int id;
	//save data input to variable word 
	public void getInput(int id1, String type1, String word1, String meaning1) {
		this.setId(id1);
		this.setWord(word1);
		this.setType(type1);
		this.setMeaning(meaning1);
	}
	//Setter//
	public void setId(int setid) {
		this.id = setid;
	}
	public void setWord(String setword) {
		this.word = setword;
	}
	public void setType(String settype) {
		this.type = settype;
	}
	public void setMeaning(String setmeaning) {
		this.meaning = setmeaning;
	}
	
	//Getter//
	public int getId() {
		return this.id;
	}	
	public String getWord() {
		return this.word;
	}	
	public String getType() {
		return this.type;
	}	
	public String getMeaning() {
		return this.meaning;
	}
	
	public int hashing(String w) {
		//TODO: funtion return hashcode of given word. 
		int hashCode = 9999;
		return hashCode;
	}
	

}
