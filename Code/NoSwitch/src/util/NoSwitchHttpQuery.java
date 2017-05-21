package util;

public class NoSwitchHttpQuery {
	private String url = "";
	
	public NoSwitchHttpQuery(String URL) {
		// TODO Auto-generated constructor stub
		this.url = URL;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{ connect to : "+url+" }";
	}
}
