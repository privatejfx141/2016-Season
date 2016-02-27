package robot;

public class Field {
	public enum Goal {
		LEFT ("Left"),
		CENTER ("Center"),
		RIGHT ("Right");
		
		private final String stringValue;
		
		Goal (String stringValue) {
			this.stringValue = stringValue;
		}
		
		public static Goal toEnum(String stringValue) {
			for(Goal goal: Goal.values()) {
				if(goal.stringValue.equals(stringValue)) {
					return goal;
				}
			}
			System.out.println("Goal value (" + stringValue + ") is not a valid goal string");
			return null;
		}
	}
	
	public enum Lane {
		CLOSE ("Close", 0,130),
		FAR ("Far", 40, 90);
		
		private final String stringValue;
		private final int driveDistance;
		private final int distanceToWall;
				
		Lane(String stringValue, int driveDistance, int distanceToWall) {
			this.stringValue = stringValue;
			this.driveDistance = driveDistance;
			this.distanceToWall = distanceToWall;
		}
		
		public int getDistanceToWall() {
			return distanceToWall;
		}
		
		public int getDriveDistance() {
			return driveDistance;
		}
		
		public static Lane toEnum(String stringValue) {
			for(Lane lane: Lane.values()) {
				if(lane.stringValue.equals(stringValue)) {
					return lane;
				}
			}
			System.out.println("Lane value (" + stringValue + ") is not a valid lane string");
			return null;
		}
	}
	
	public enum Slot { 
		ONE   (1, 10), 
		TWO   (2, 73), 
		THREE (3, 110), 
		FOUR  (4, 160), 
		FIVE  (5, 215); 
	
		private final int intValue;
		private final double distanceToLeftWall;
		
		Slot (int intValue, double distanceToLeftWall) {
			this.intValue = intValue;
			this.distanceToLeftWall = distanceToLeftWall;
		}
		
		public static Slot toEnum(int intValue) {
			for (Slot slot: Slot.values()) {
				if (slot.intValue == intValue)  {
					return slot;
				}
			}
			System.out.println("Slot value (" + intValue + ") is not a valid slot");
			return null;
		}

		public double getDistanceToLeftWall() {
			return distanceToLeftWall;
		}
	}
	
	public enum Defense {
		LOW_BAR         ("Low Bar", 152),
		RAMPARTS        ("Ramparts", 172),
		MOAT            ("Moat", 172),
		ROCK_WALL       ("Rock Wall", 172),
		ROUGH_TERRAIN   ("Rough Terrain", 172),
		PORTCULLIS      ("Portcullis", 172),
		CHEVAL_DE_FRISE ("Cheval de Frise", 172); 
	
		private final String stringValue;
		private final int driveDistance;
		
		Defense (String stringValue, int driveDistance) {
			this.stringValue = stringValue;
			this.driveDistance = driveDistance;
		}
		
		public int getDriveDistance() {
			return driveDistance;
		}
		
		public static Defense toEnum(String stringValue) {
			for (Defense defense: Defense.values()) {
				if (defense.stringValue.equals(stringValue))  {
					return defense;
				}
			}
			System.out.println("Defense value (" + stringValue + ") is not a valid defense string");
			return null;
		}

	}
}
