import java.util.Scanner;

class RoomAdventure {

    // class variables
    private static Room currentRoom;
    private static String[] inventory = {null, null, null, null, null};
    private static String status;


    final private static String DEFAULT_STATUS = "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include 'go', 'look', and 'take'.";

    public static void main(String[] args){
         
        setupGame();

        // while loops
        while (true){
            // outputting
            System.out.println(currentRoom.toString());
            System.out.print("Inventory: ");

            // for loops
            for (int i = 0; i < inventory.length; i++){
                System.out.print(inventory[i] + " ");
            }


            System.out.println("\nWhat would you like to do? ");
        

            // taking input
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            
            // .split() takes a string and turns it into an array
            // each item is the array is divided by the arg in split()
            String[] words = input.split(" ");


            if (words.length < 2) {
                status = DEFAULT_STATUS;
                System.out.println(status);
                continue;  // skip back to the top of the while loop
            }

            String verb = words[0];
            String noun = words[1];

            switch (verb){
                case "go":
                    handleGo(noun);
                    break;
                case "look":
                    handleLook(noun);
                    break;
                case "take":
                    handleTake(noun);
                    break;   
                default: status = DEFAULT_STATUS;
            }
            
            System.out.println(status);            
        }

    }

    private static void handleGo(String noun){
        status = "I don't see that room";
        String[] directions = currentRoom.getExitDirections();
        Room[] rooms = currentRoom.getExitDestinations();

        for (int i=0; i < directions.length; i++){
            // for strings we use .equals() to compare 
            if (noun.equals(directions[i])){
                currentRoom = rooms[i];
                status = "Changed Room";
            }
        }
    }

    private static void handleLook(String noun){
        status = "I don't see that item.";
        String[] items = currentRoom.getItems();
        String[] descriptions = currentRoom.getItemDescriptions();

        for (int i=0; i < items.length; i++){
            if (noun.equals(items[i])){
                status = descriptions[i];
            }
        }
    }

    private static void handleTake(String noun){
        status = "I can't grab that.";
        String[] grabs = currentRoom.getGrabbables();

        for (int i = 0; i < grabs.length; i++){
            if (noun.equals(grabs[i])){

                // maybe make a addToInventory() func?
                // maybe expand the inventory to any number of items
                for (int j=0; j < inventory.length; j++){
                    if (inventory[j] == null){
                        inventory[j] = noun;
                        // maybe say what item was added?
                        status = "Added item to inventory";
                        if(noun.equals("paper")){
                            if(currentRoom.getName().equals("Room 3")){
                               status += " — A note on the paper says: 'Check Room 2.'"; 
                            } else if(currentRoom.getName().equals("Room 2")){
                                status += " — A scribble reads: 'Something is in Room 1...'";
                            }                            
                            // Hint for the player
                            status += " (Commit this to memory.)";
                        }
                        break;
                    }
                }
            }
        }
    }

    private static void setupGame(){
        Room room1 = new Room("Room 1"); // instantiation of an object
        Room room2 = new Room("Room 2");
        // Room 3
        Room room3 = new Room("Room 3");
        //Room 4
        Room room4 = new Room("Room 4");

        // Room 1
        String[] room1ExitDirections = {"east", "north"}; // declaring an array
        Room[]   room1ExitDestinations = {room2, room3};

        String[] room1Items = {"chair", "desk"};
        String[] room1ItemDescriptions = {
            "It is a chair", 
            "Its a desk, there is a key on it."
        };

        String[] room1Grabbables = {"key"};

        room1.setExitDirections(room1ExitDirections);
        room1.setExitDestinations(room1ExitDestinations);
        room1.setItems(room1Items);
        room1.setItemDescriptions(room1ItemDescriptions);
        room1.setGrabbables(room1Grabbables);

        // Room 2
        String[] room2ExitDirections = {"west", "east"};
        Room[]   room2ExitDestinations = {room1, room4};
        String[] room2Items = {"fireplace", "rug"};
        String[] room2ItemDescriptions = {
            "Its on fire, wait! whats that paper?", 
            "There is a lump of coal on the rug."
        };

        String[] room2Grabbables = {"paper"};
        room2.setExitDirections(room2ExitDirections);
        room2.setExitDestinations(room2ExitDestinations);
        room2.setItems(room2Items);
        room2.setItemDescriptions(room2ItemDescriptions);
        room2.setGrabbables(room2Grabbables);

        // Room 3
        String[] room3ExitDirections = {"south"};
        Room[]   room3ExitDestinations = {}; // temporary

        String[] room3Items = {"bookshelf", "painting"};
        String[] room3ItemDescriptions = {
            "A dusty bookshelf full of old books. A piece of paper sticks out.",
            "A creepy painting nothing to see here."
        };

        String[] room3Grabbables = {"paper"};

        room3.setExitDirections(room3ExitDirections);
        room3.setExitDestinations(new Room[]{ room1 }); // setting exit to room1
        room3.setItems(room3Items);
        room3.setItemDescriptions(room3ItemDescriptions);
        room3.setGrabbables(room3Grabbables);
        
        //Room 4
        String[] room4ExitDirections = {"west"};
        Room[]   room4ExitDestinations = {};
        
        String[] room4Items = {"mirror", "statue"};
        String[] room4ItemDescriptions = {
            "An old cracked mirror — your reflection appears distorted.",
            "A statue made of stone."
        };
        String[] room4Grabbables = {"gem"};
        room4.setExitDirections(room4ExitDirections);
        room4.setExitDestinations(new Room[]{ room2 });
        room4.setItems(room4Items);
        room4.setItemDescriptions(room4ItemDescriptions);
        room4.setGrabbables(room4Grabbables);

        //Start off here
        currentRoom = room1;
    }
}

class Room {

    private String name;
    private String[] exitDirections;    // north, south, east, west
    private Room[] exitDestinations;
    private String[] items;
    private String[] itemDescriptions;
    private String[] grabbables;

    // constructors - function has same name as class
    public Room(String name){
        this.name = name; // use this to refer to the instance when it is unclear
    }

    public String getName(){
        return name;
    }
    // other methods
    public void setExitDirections(String[] exitDirections){
        this.exitDirections = exitDirections;
    }
    

    public String[] getExitDirections(){
        return exitDirections;
    }
    
    public void setExitDestinations(Room[] exitDestinations){
        this.exitDestinations = exitDestinations;
    }

    public Room[] getExitDestinations(){
        return exitDestinations;
    }

    public void setItems(String[] items){
        this.items = items;
    }

    public String[] getItems(){
        return items;
    }
    
    public void setItemDescriptions(String[] itemDescriptions){
        this.itemDescriptions = itemDescriptions;
    }

    public String[] getItemDescriptions(){
        return itemDescriptions;
    }


    public String[] getGrabbables(){
        return grabbables;
    }

    public void setGrabbables(String[] grabbables){
        this.grabbables = grabbables;
    }

    public String toString(){
        String result = "\n";
        result += "Location: " + name;

        result += "\nYou See: ";
        // for i loop
        for (int i = 0; i < items.length; i++){
            result += items[i] + " ";
        }

        result += "\nExits: ";
        // for each loop
        for (String direction : exitDirections){
            result += direction + " ";
        }

        return result + "\n";
    }
}
