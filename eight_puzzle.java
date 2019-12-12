import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
/* Viraj Vasantlal B819581
 * AI Method Coursework
 * Eight Puzzle Source Code 
 * */
public class eight_puzzle extends ArrayList {
	/* Each State will be represented by a ArrayLisy of Strings I.E [a,b,c,d,e,f,g,h,_]. 
	   HashSet all_states will store all the uniquely explored states. Hashset has been
	   used for performance where a state search complexity will improve from 
	   O(n) for ArrayList to O(1) for HashSet. 
	   ArrayList stack will act as a LIFO data structure that will allow a depth-first 
	   traversal of the possible states.
	   ArrayList states_to_write will be a clone of all_states that will store starates
	   in the order they were found in.*/
	static HashSet<ArrayList> all_states = new HashSet<ArrayList>(); 
	static ArrayList<ArrayList> stack = new ArrayList<ArrayList>();
	static ArrayList<ArrayList> states_to_write = new ArrayList<ArrayList>();
	
	/* generate_states function will receive the root state and it will iteratively 
	   generate new states in depth first order by using the data in the stack.*/
	public static void generate_states(ArrayList<String> start_state) {
		all_states.add(start_state);
		stack.add(start_state);
		states_to_write.add(start_state); 
		/* Until the stack is empty new states will be generated and added to
		   all_states iteratively */
		while (stack.size()!=0) { 
			ArrayList<String> popped_state=
					(ArrayList<String>) stack.get(stack.size()-1).clone();
			generate_new_state(popped_state);  
		}
	}
	
	/* generate_new_state function will get a popped state from the stack then 
	   create a new state from that by making a possible move UP,DOWN,RIGHT,
	   or LEFT and then add this state to all_states (search tree) if it doesn't 
	   already exists in it.*/ 
	public static void generate_new_state(ArrayList<String> popped_state) {
		/*this boolean will check if a new state was added to all_states*/
		boolean new_state_created = false;
		/*this ArrayList will get all possible moves that can be made from the state 
		 * given*/
		ArrayList<ArrayList> available_states = available_states(popped_state);
		/* iterate through the available states to see if they can be added*/
		for (ArrayList new_state : available_states) {
			if (all_states.contains(new_state)) {
				new_state_created = false;
			} else {
				all_states.add(new_state);
				stack.add(new_state);
				states_to_write.add(new_state);
				new_state_created=true;
				break;
			}
		}
		/*if a new state was not created from the popped state then popped state can 
		 * be removed from the stack as no non-duplicate states will be found and 
		 * the program will backtrack*/
		if (new_state_created==false) {
			stack.remove(stack.size()-1);
		}
	}
	
	/*available_states function will take in a state and will return a list of all 
	  possible states that can be reached from that state. This function will make 
	  use of abstraction of the 3x3 grid to an array that is explained on the report 
	  to check which blank space moves are possible */
	public static ArrayList<ArrayList> available_states(ArrayList<String> state) {
		ArrayList<ArrayList> available_states = new ArrayList<ArrayList>();
		int blank_index = state.indexOf("_");
		
		//if possible, create new state by moving blank up.
		if (blank_index>2){ 
			ArrayList<String> up_state = (ArrayList<String>) state.clone();
			up_state.set(blank_index, state.get(blank_index-3));
			up_state.set(blank_index-3,"_");
			available_states.add(up_state);
		}
		//if possible, create new state by moving blank down.
		if (blank_index<6){ 
			ArrayList<String> down_state = (ArrayList<String>) state.clone();
			down_state.set(blank_index, state.get(blank_index+3));
			down_state.set(blank_index+3,"_" );
			available_states.add(down_state);
		}
		//if possible, create new state by moving blank right.
		if ((blank_index!=2) && (blank_index!=5) && (blank_index!=8)){
			ArrayList<String> right_state = (ArrayList<String>) state.clone();
			right_state.set(blank_index, state.get(blank_index+1));
			right_state.set(blank_index+1,"_" );
			available_states.add(right_state);
		}
		//if possible, create new state by moving blank left.
		if ((blank_index!=0) && (blank_index!=3) && (blank_index!=6)){
			ArrayList<String> left_state = (ArrayList<String>) state.clone();
			left_state.set(blank_index, state.get(blank_index-1));
			left_state.set(blank_index-1,"_" );
			available_states.add(left_state);
		}
		return available_states;
	}
	
	/*format function will take in a state as a parameter and 
	  return a printable state grid string*/
	public static String format(ArrayList<String> state) {
		return state.get(0)+" | "+state.get(1)+" | "+state.get(2)+
				"\n"+state.get(3)+" | "+state.get(4)+" | "+state.get(5)+
				"\n"+state.get(6)+" | "+state.get(7)+" | "+state.get(8)+"\n";
	}
	
	/*write_to_file function will write grid string of each state in states_to_write
	  to a .txt file taken as a parameter*/
	public static void write_to_file(String file) {
		try { 
			FileWriter fr = new FileWriter(file+".txt",false );
			for (ArrayList state : states_to_write) {
				fr.write(format(state)+"\n");
			}
			fr.close();
		} catch(FileNotFoundException e) {
				e.printStackTrace();
		} catch(IOException e) {
				e.printStackTrace();
		}
	}
	
	/*state_difference will take in all states that were generated by input S1 and S2
	  and will output the difference between R(S1) and R(S2).The function will
	  use observation that if a single element in R(S1) is in R(S2), then it means 
	  that R(S1)=R(S2) hence R(S1)\R(S2) will be empty or equal to R(S1)*/
	public static void state_difference(ArrayList<ArrayList> s1_states,
			ArrayList<ArrayList> s2_states) {
		
		if (s2_states.contains(s1_states.get(0))) {
			states_to_write.clear();
			write_to_file("R(S1)-R(S2)");
		} else {
			states_to_write = (ArrayList<ArrayList>) s1_states.clone();
			write_to_file("R(S1)-R(S2)");
		}
		System.out.println("R(S1)\\R(S2) generated to R(S1)-R(S2).txt");
	}
	public static void main(String[] args) { 

		Scanner input = new Scanner(System.in);
		
		System.out.print("INPUT S1: ");//Get S1 input
		String input_state = input.next();
		ArrayList<String> s1_input= new ArrayList<String>(Arrays.asList
				(input_state.split(",")));//Turn input into a ArrayList 
		
		System.out.print("INPUT S2: ");//Get S2 input
		input_state = input.next();
		ArrayList<String> s2_input= new ArrayList<String>(Arrays.asList
				(input_state.split(",")));//Turn input into a ArrayList 
		
		try { //Try method to catch IO errors
			//Generate all states for input S1
			generate_states(s1_input); 
			ArrayList<ArrayList> s1_states = (ArrayList<ArrayList>) 
											states_to_write.clone(); 
			write_to_file("R(S1)"); //Generate states to File
			System.out.println("R(S1) generated to R(S1).txt");
			
			all_states.clear();//Clear all the ArrayLists 
			stack.clear();
			states_to_write.clear();
			
			//Generate all states for input S2
			generate_states(s2_input);
			ArrayList<ArrayList> s2_states = (ArrayList<ArrayList>) 
											 states_to_write.clone();
			write_to_file("R(S2)");//Generate states to File 
			System.out.println("R(S2) generated to R(S2).txt");
			
			state_difference(s1_states,s2_states);//Generate R(S1)\R(S2)
			
			System.out.println("|R(S1)| = "+ s1_states.size());
			System.out.println("|R(S2)| = "+ s2_states.size());
			System.out.println("|R(S1)\\R(S2)| = "+ states_to_write.size());
			
		} catch (Exception e) {
			System.out.println("Error - Input Invalid , Run Agian!");
		}
	}
}
