// CS 0445 Spring 2022
// LinkedListPlus<T> class partial implementation

// See the commented methods below.  You must complete this class by
// filling in the method bodies for the remaining methods.  Note that you
// may NOT add any new instance variables, but you may use method variables
// as needed.

public class LinkedListPlus<T> extends A2LList<T> {
	// Default constructor simply calls super()
	public LinkedListPlus() {
		super();
	}

	// Copy constructor.  This is a "deepish" copy so it will make new
	// Node objects for all of the nodes in the old list.  However, it
	// is not totally deep since it does NOT make copies of the objects
	// within the Nodes -- rather it just copies the references.  The
	// idea of this method is as follows:  If oldList has at least one
	// Node in it, create the first Node for the new list, then iterate
	// through the old list, appending a new Node in the new list for each
	// Node in the old List.  At the end, link the Nodes around to make sure
	// that the list is circular.
	public LinkedListPlus(LinkedListPlus<T> oldList) {
		super();
		//if oldList > 0 ...
		if (oldList.getLength() > 0) {
			//create the first Node for the new List
			//... for loop/while loop *may need a count variable* - append a new Node in the new List for each Node in the old List
			// make the new List circular (mod?)*check the end of the copy constructor*
		}

		{
			// Special case for first Node since we need to set the
			// firstNode instance variable.
			Node temp = oldList.firstNode;
			Node newNode = new Node(temp.data);
			firstNode = newNode;

			// Now we traverse the old list, appending a new Node with
			// the correct data to the end of the new list for each Node
			// in the old list.  Note how the loop is done and how the
			// Nodes are linked.
			Node currNode = firstNode;
			temp = temp.next;
			int count = 1;
			while (count < oldList.getLength()) {
				//the newNode is now the next Node in the list
				newNode = new Node(temp.data);
				//the current node in newList is the first node in newList
				currNode.next = newNode;
				//the previous node is the first node
				newNode.prev = currNode;
				//the old first node is assigned to the next node
				temp = temp.next;
				//the current node in the new List is assigned to next node in the new List
				currNode = currNode.next;
				//incrementing the count variable for the while loop
				count++;
			}
			currNode.next = firstNode;  // currNode is now at the end of the list.
			firstNode.prev = currNode;    // link to make the list circular *!!!!!!!!!!*
			numberOfEntries = oldList.numberOfEntries;
		}
	}

	// Make a StringBuilder then traverse the nodes of the list, appending the
	// toString() of the data for each node to the end of the StringBuilder.
	// Finally, return the StringBuilder as a String.  Note that since the list
	// is circular, we cannot look for null.  Rather we must count the Nodes as
	// we progress down the list.
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		Node curr = firstNode;
		int i = 0;
		while (i < this.getLength()) {
			b.append(curr.data.toString());
			b.append(" ");
			curr = curr.next;
			i++;
		}
		return b.toString();
	}

	// Remove num items from the front of the list
	public void leftShift(int num) {
		//counter variable for the loops
		int count = num;
		//while counter(num) is greater than 0
		while (count > 0) {
			//if the num is greater than the length of the list then the list will be cleared completely
			if (count > this.getLength()) {
				clear();
				break;
				//but if count(num) is less than or equal to 0, everything will equal itself
			} else if (count <= 0) {

				firstNode.setNextNode(firstNode.next);
			}

			for(int i = 0; i < num; i++){
				//next node becomes the first node, pushing the original first node to the back of the list
				firstNode = firstNode.next;
				//take away original first node
				numberOfEntries--;
				//set new first node's previous node to the next previous node after its already previous node (1..0..9)
				firstNode.setPrevNode(firstNode.prev.prev);
				//set the new previous node's previous node to the new first node
				firstNode.prev.setNextNode(firstNode);
				//decrement count
				count--;

			}

		}


	}

	// Remove num items from the end of the list
	public void rightShift(int num) {
		//counter variable
		int count = num;
		//while counter(num) is greater than 0
		while (count > 0) {
			//if the num is greater than the length of the list then the list will be cleared completely
			if (count > this.getLength()) {
				clear();
				break;
				//but if count(num) is less than or equal to 0, everything will equal itself
			} else if (count <= 0) {
				firstNode.setNextNode(firstNode.next);
			}

			for(int i = 0; i < num; i++){
				//take out the last node in the list
				numberOfEntries--;
				//now the first node's previous node will be the previous node after it's original previous node
				firstNode.setPrevNode(firstNode.prev.prev);
				//previous node's next node will now be the first node in the list
				firstNode.prev.setNextNode(firstNode);
				//decrement count
				count--;
			}


		}
	}

		// Rotate to the left num locations in the list.  No Nodes
		// should be created or destroyed.
		public void leftRotate ( int num)
		{
			//define a counter variable
			int count = num;
			//while counter is not equal to the num (testing if there is a negative number - we will simply use the code for rotating right)
			while(count < 0){
				//assign the first node to the last node
				firstNode = firstNode.prev;
				//increment count
				count++;
			}
			//(if num is not negative) while count is greater than 0...
			while(count > 0){
				for(int i = 0; i < num; i++){
					//assign the first node to the next node
					firstNode = firstNode.next;
					//if the num is greater than the length of the list
					if(num > getLength()){
						//num will rotate the remainder of itself modulo the length of the list
						num = num % getLength();
					}
					//decrement count
					count--;
				}
			}


		}

		// Rotate to the right num locations in the list.  No Nodes
		// should be created or destroyed.
		public void rightRotate ( int num)
		{
			//define a counter
			int count = num;
			//while count(num) is less than 0 (testing for a negative num - we will simply use the leftRotate logic)
			while(count < 0){
				//assign first node to the next node
				firstNode = firstNode.next;
				//increment count
				count++;
			}
			//(if num is not negative) while count is greater than 0...
			while(count > 0){
				//assign first node to the previous node
				firstNode = firstNode.prev;

				//if num is greater than the length of the list...
				if(num > getLength()){
					//num will simply equal the remainder of itself modulo the list's length
					num = num % getLength();
				}
				//decrement the count variable
				count--;
			}

		}
	}
