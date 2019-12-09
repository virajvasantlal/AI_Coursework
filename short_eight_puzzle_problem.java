import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.AbstractList;

public class short_eight_puzzle_problem extends ArrayList {
	
	static HashSet<ArrayList> states = new HashSet<ArrayList>();
	static ArrayList<ArrayList> stack = new ArrayList<ArrayList>();
	static boolean all_generated = false;
	
	public static void generate_states() {
		while (all_generated==false) {
			ArrayList<String> last_state = (ArrayList<String>) stack.get(stack.size()-1).clone();
			generate_new_state(last_state);  
			if (stack.size()==0) {
				all_generated=true;
			}
		}
	}
	
	public static void generate_new_state(ArrayList<String> state) {
		boolean new_state_created = false;
		ArrayList<ArrayList> available_states = available_states(state);
		
		for (ArrayList available_state : available_states) {
			if (states.contains(available_state)) {
				new_state_created = false;
			} else {
				states.add(available_state);
				stack.add(available_state);
				System.out.println(available_state+"  "+states.size());
				new_state_created=true;
				break;
			}
		}

		if (new_state_created==false) {
			stack.remove(stack.size()-1);
		}
		
	}
	
	public static ArrayList<ArrayList> available_states(ArrayList<String> state) {
		ArrayList<ArrayList> available_states = new ArrayList<ArrayList>();
		ArrayList<String> up_state = (ArrayList<String>) state.clone();
		ArrayList<String> down_state = (ArrayList<String>) state.clone();
		ArrayList<String> right_state = (ArrayList<String>) state.clone();
		ArrayList<String> left_state = (ArrayList<String>) state.clone();
		
		int blank_index = state.indexOf("+");
		
		if (blank_index>2){
			up_state.set(blank_index, state.get(blank_index-3));
			up_state.set(blank_index-3,"+");
			available_states.add(up_state);
		}
		if (blank_index<6){
			down_state.set(blank_index, state.get(blank_index+3));
			down_state.set(blank_index+3,"+" );
			available_states.add(down_state);
		}
		if ((blank_index!=2) && (blank_index!=5) && (blank_index!=8)){
			right_state.set(blank_index, state.get(blank_index+1));
			right_state.set(blank_index+1,"+" );
			available_states.add(right_state);
		}
		if ((blank_index!=0) && (blank_index!=3) && (blank_index!=6)){
			left_state.set(blank_index, state.get(blank_index-1));
			left_state.set(blank_index-1,"+" );
			available_states.add(left_state);
		}
		return available_states;
	}
	
	
	
	public static void main(String[] args) {
	
		ArrayList<String> state1 = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","+"));
		states.add(state1);
		stack.add(state1);
		System.out.println("[a, b, c, d, e, f, g, h, +]  1");
		generate_states();


	}
	
	
	
}
