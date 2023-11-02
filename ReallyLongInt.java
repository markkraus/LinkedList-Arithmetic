// CS 0445 Spring 2022
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.


public class ReallyLongInt 	extends LinkedListPlus<Integer>
							implements Comparable<ReallyLongInt>
{
	private ReallyLongInt()
	{
		super();
	}

	// Data is stored with the LEAST significant digit first in the list.  This is
	// done by adding all digits at the front of the list, which reverses the order
	// of the original string.  Note that because the list is doubly-linked and
	// circular, we could have just as easily put the most significant digit first.
	// You will find that for some operations you will want to access the number
	// from least significant to most significant, while in others you will want it
	// the other way around.  A doubly-linked list makes this access fairly
	// straightforward in either direction.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit = -1;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				// Do not add leading 0s
				if (!(digit == 0 && this.getLength() == 0))
					this.add(1, Integer.valueOf(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
		// If number is all 0s, add a single 0 to represent it
		if (digit == 0 && this.getLength() == 0)
			this.add(1, Integer.valueOf(digit));
	}

	// Copy constructor can just call super()
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}

	// Constructor with a long argument.  You MUST create the ReallyLongInt
	// digits by parsing the long argument directly -- you cannot convert to a String
	// and call the constructor above.  As a hint consider the / and % operators to
	// extract digits from the long value.
	public ReallyLongInt(long X)
	{
		int x = (int) X;
		if(X == 0 && this.getLength() == 0) {
			this.add(1,Integer.valueOf(x));
		}
		while(X > 0){
			int r = (int) (X % 10);
			if (!(X == 0 && this.getLength() == 0)){
				this.add(1, Integer.valueOf(r));
			}
			this.firstNode = this.firstNode.next;

			X = X /10;
		}



	}

	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		Node curr = firstNode.prev;
		int i = 0;
		while (i < this.getLength()) {
			b.append(curr.data.toString());
			curr = curr.prev;
			i++;
		}
		return b.toString();
	}

	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.  Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	public ReallyLongInt add(ReallyLongInt rightOp)
	{
		ReallyLongInt Z = new ReallyLongInt();
		Node currT = firstNode;
		Node currR = rightOp.firstNode;
		int carry = 0;

		if(this.getLength() == rightOp.getLength()) {
			for (int i = 0; i <= rightOp.getLength(); i++) {
				int sum = currT.data + currR.data + carry;
				carry = 0;
				if (sum >= 10) {
					carry = 1;
					sum = sum % 10;
				}

				if (!(sum == 0 && this.getLength() == 0)) {
					Z.add(1, Integer.valueOf(sum));
					Z.leftRotate(1);

				}
				currT = currT.next;
				currR = currR.next;


			}
		}else if(this.getLength() < rightOp.getLength()){

			while(Z.numberOfEntries < 1){
				int sum = currT.data + currR.data;
				if(sum >= 10){
					carry = 1;
					sum %= 10;
				}
				if (!(sum == 0 && numberOfEntries == 0)) {
					Z.add(1, Integer.valueOf(carry));
					Z.leftRotate(1);

				}
			}
				for(int i = 0; i < rightOp.numberOfEntries; i++){
					int sum =  currR.data + carry;
					carry = 0;
					if(sum <= 10){
						carry= 1;
						sum %= 10;
					}
					if (!(sum == 0 && numberOfEntries == 0)) {
						Z.add(1, Integer.valueOf(sum));


					}
				}
		}else if(rightOp.numberOfEntries < numberOfEntries){
			while(Z.numberOfEntries < 1){
				int sum = currT.data + currR.data;
				if(sum >= 10){
					carry = 1;
					sum %= 10;
				}
				if (!(sum == 0 && numberOfEntries == 0)) {
					Z.add(1, Integer.valueOf(carry));
					Z.leftRotate(1);

				}
			}
			for(int i = 0; i < numberOfEntries; i++){
				int sum =  currT.data + carry;
				carry = 0;
				if(sum <= 10){
					carry= 1;
					sum %= 10;
				}
				if (!(sum == 0 && rightOp.numberOfEntries == 0)) {
					Z.add(1, Integer.valueOf(sum));


				}
			}
		}else{
			Z.add(1,0);
		}


			return Z;
	}

	// Return new ReallyLongInt which is difference of current and argument
	public ReallyLongInt subtract(ReallyLongInt rightOp)
	{
		ReallyLongInt Z = new ReallyLongInt();
		Node currT = firstNode;
		Node currR = rightOp.firstNode;
		int carry = 0;
		int entries = 0;
		if(compareTo(rightOp) == 1){
			while(entries < rightOp.numberOfEntries){
				int diff = currT.data - currR.data;
				if(diff < 0){
					if(currT.next.data == 0){
						carry = (currT.data + 10) - currR.data;
					}else{
						currT.next.data -= 1;
						carry = (currT.data + 10) - currR.data;
					}
					if (!(diff == 0 && this.getLength() == 0)) {
						Z.add(1, Integer.valueOf(carry));
						Z.leftRotate(1);
					}
				}else{
					carry = diff;
					if (!(diff == 0 && this.getLength() == 0)) {
						Z.add(1, Integer.valueOf(carry));
						Z.leftRotate(1);
					}
				}
				entries++;
				currT = currT.next;
				currR = currR.next;
			}
		}else if(compareTo(rightOp) == 0){
			Z.add(1,0);
		}else{
			throw new ArithmeticException("Cannot subtract");
		}

		if(currT.data > 0 && numberOfEntries > rightOp.numberOfEntries){
			Z.add(Z.numberOfEntries + 1,currT.data);
		}
		Node currZ = Z.firstNode;
		while(currZ.prev.getData() == 0 && compareTo(rightOp) != 0){
			currZ.prev.setData(null);
			currZ.setPrevNode(currZ.prev.prev);
			Z.numberOfEntries--;

		}
		if(Z.firstNode.getData() == 9){
			while(entries + 1 < numberOfEntries){
				Z.add(1,9);
				entries++;
			}
		}



		return Z;

	}

	// Return new ReallyLongInt which is product of current and argument
	public ReallyLongInt multiply(ReallyLongInt rightOp) {
      int len1 = this.getLength();
      int len2 = rightOp.getLength();
      int[] result = new int[len1 + len2];

      // Initialize the result array with 0s
      for (int i = 0; i < len1 + len2; i++) {
        result[i] = 0;
      }

      Node curr1 = this.firstNode;
      int i_n1 = 0;

      // Outer loop for the first ReallyLongInt
      while (curr1 != this.firstNode || i_n1 == 0) {
        int carry = 0;
        int n1 = curr1.getData();
        Node curr2 = rightOp.firstNode;
        int i_n2 = 0;

        // Inner loop for the second ReallyLongInt
        while (curr2 != rightOp.firstNode || i_n2 == 0) {
          int n2 = curr2.getData();

          // Multiply the current digits and add it to the result
          int sum = n1 * n2 + result[i_n1 + i_n2] + carry;
          carry = sum / 10;
          result[i_n1 + i_n2] = sum % 10;

          i_n2++;
          curr2 = curr2.next;
        }

        if (carry > 0) {
          result[i_n1 + i_n2] += carry;
        }

        i_n1++;
        curr1 = curr1.next;
      }

      // Remove leading zeros if any
      int i = len1 + len2 - 1;
      while (i > 0 && result[i] == 0) {
        i--;
      }

      // Construct the final ReallyLongInt from the result array
      ReallyLongInt res = new ReallyLongInt();
      for (int j = i; j >= 0; j--) {
        res.add(1, result[j]);
      }

      return res;
    }

	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt rOp) {
		Node currThis = firstNode.prev;
		Node currROp = rOp.firstNode.prev;
		if (this.getLength() > rOp.getLength()) {
			return 1;
		} else if(this.getLength() < rOp.getLength()){
			return -1;
		}else{
			for(int i = 0; i <= this.getLength(); i++){
				if(currThis.data == currROp.data){
					currThis = currThis.prev;
					currROp = currROp.prev;
				}else if(currThis.data != currROp.data){
					if(currThis.data > currROp.data){
						return 1;
					}else{
						return -1;
					}
				}
			}

		}


		return 0;
	}

	// Is current ReallyLongInt equal to rightOp?  Note that the argument
	// in this case is Object rather than ReallyLongInt.  It is written
	// this way to correctly override the equals() method defined in the
	// Object class.
	public boolean equals(Object rightOp)
	{
		ReallyLongInt Z = (ReallyLongInt) rightOp;
		if(compareTo(Z) == 0){

		}else{
			return false;
		}
		return true;
	}
}
