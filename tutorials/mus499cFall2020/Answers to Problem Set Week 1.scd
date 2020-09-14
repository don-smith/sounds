//(Answers to) Problem Set Week 1

//Problem 1
//The following code produces an error. Correct the error and add a comment indicating the problem (comments are created by starting a line with two forward slashes, exactly like this line).

(
var apples; //apples was not declared as a local variable.
apples = 3;
apples = apples * 2;
apples = apples - 1;
)



//Problem 2
//The following code produces an error. Correct the error and add a comment indicating the problem.

(
//the var declaration line was missing a semicolon at the end.
var oldBalance, deposit, newBalance;
oldBalance = 5;
deposit = 100;
newBalance = oldBalance + deposit;
)



//Problem 3
//The following code produces an error. Correct the error and add a comment indicating the problem.

(
//"totalStudents" was misspelled.
var classroomSize, numClasses, totalStudents;
classroomSize = 30;
numClasses = 4;
totalStudents = classroomSize * numClasses;
)



//Problem 4
//The following code produces an error. Correct the error and add a comment indicating the problem.

(
~func = {
	//there was a comma immediately following 'arg' that needed to be deleted.
	arg x=5, y=6, z=7;
	var product;
	product = x * y * z;
};
)



//Problem 5
//The following code returns a boolean indicating whether the square of 45 is an odd number, and is written in the "receiver.method" style. Rewrite it in the "method(receiver)" style.

45.squared.odd;

//rewritten:
odd(squared(45));



//Problem 6
//The following conditional check prints whether a random float between 0 and 1 is greater than 0.5 or not, and is written in the "method(receiver)" style. Rewrite it so that all instances of this syntax style are converted to the "receiver.method" style.

(
if(
	postln(rand(1.0)) > 0.5,
	{postln("is greater than 0.5")},
	{postln("is not greater than 0.5")}
);
)

//rewritten:
(
(1.0.rand.postln > 0.5).if(
	{"is greater than 0.5".postln},
	{"is not greater than 0.5".postln}
);
)



//Problem 7
//Write a function that accepts three input arguments. If all three inputs are members of the same class (e.g. 1, 4, 500), return the string "same". If not (e.g. "hello", 6.1, 99), return the string "different". Write an additional few lines of code that demonstrate the use of this function.

(
~samenessCheck = {
	arg a, b, c;
	if(
		(a.class == b.class) && (b.class == c.class),
		{"same"},
		{"different"}
	);
};
)

~samenessCheck.value(1, 4, 500);
~samenessCheck.value("hello", 6.1, 99);
~samenessCheck.value("hi","foo","bar");
~samenessCheck.value("hi", [3], $b);



//Problem 8
//Write a function that checks whether a number is a multiple of 100. If so, return that number. If not, return the nearest multiple of 100. If the input  provided is not a number, return the string "not a number". Write an additional few lines of code that demonstrate the use of this function.

(
~multipleCheck = {
	arg num;
	if(
		(num.class == Integer) || (num.class == Float),
		{
			if(
				num.round(100) == num,
				{num},
				{num.round(100)}
			)
		},
		{"not a number"}
	);
};
)

~multipleCheck.value(1000);
~multipleCheck.value(1062.0);
~multipleCheck.value(-451.0);
~multipleCheck.value("number");



//Problem 9
//Write a function that checks whether an incoming value (representing a frequency memasured in Hz) is within the generally-accepted audible range of human hearing (not less than 20 and not greater than 20000). If it's within range, return the string: "play tone at xxx Hz". If it's not within this range, return the string "xxx Hz not within range". The actual number should replace the "xxx" in both cases. (Note: you can use the method "asString" to convert a number to a string. If the input is not a number, the function should return the string "invalid input". Write an additional few lines of code that demonstrate the use of this function.

(
~toneCheck = {
	arg freq;
	if(
		(freq.class == Integer) || (freq.class == Float),
		{
			if(
				(freq >= 20) && (freq <= 20000),
				{"play tone at " ++ freq.asString ++ " Hz"},
				{freq.asString ++ " Hz not within range"},
			);
		},
		{
			"invalid input"
		}
	);
};
)

~toneCheck.value(150.7);
~toneCheck.value(4030);
~toneCheck.value(20001);
~toneCheck.value(-312.99);
~toneCheck.value("mellow frequencies");