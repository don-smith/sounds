//Problem Set Week 1
//Due 3pm CDT Sept 3rd

//First, save this scd file as a new file, renaming it with your Last name at the beginning of the file name (e.g. "Fieldsteel Problem Set Week 1.scd"), and upload this renamed file to the course website for your submission.

//Problem 1
//The following code produces an error. Correct the error and add a comment indicating the problem (comments are created by starting a line with two forward slashes, exactly like this line).
// Don: missing `var` on line 11

(
var apples = 3;
apples = apples * 2;
apples = apples - 1;
)



//Problem 2
//The following code produces an error. Correct the error and add a comment indicating the problem.
// Don: missing trailing semicolon on line 22

(
var oldBalance, deposit, newBalance;
oldBalance = 5;
deposit = 100;
newBalance = oldBalance + deposit;
)



//Problem 3
//The following code produces an error. Correct the error and add a comment indicating the problem.
// Don: `totalStudents` misspelled on line 36

(
var classroomSize, numClasses, totalStudents;
classroomSize = 30;
numClasses = 4;
totalStudents = classroomSize * numClasses;
)



//Problem 4
//The following code produces an error. Correct the error and add a comment indicating the problem.
// Don: shouldn't be a comma after `arg` on line 50

(
~func = {
	arg x=5, y=6, z=7;
	var product;
	product = x * y * z;
};
)



//Problem 5
//The following code returns a boolean indicating whether the square of 45 is an odd number, and is written in the "receiver.method" style. Rewrite it in the "method(receiver)" style.

squared(45).odd;



//Problem 6
//The following conditional check prints whether a random float between 0 and 1 is greater than 0.5 or not, and is written in the "method(receiver)" style. Rewrite it so that all instances of this syntax style are converted to the "receiver.method" style.

(
if(
	(1.0).rand.postln > 0.5,
	{"is greater than 0.5".postln},
	{"is not greater than 0.5".postln}
);
)




//Problem 7
//Write a function that accepts three input arguments. If all three inputs are members of the same class (e.g. 1, 4, 500), return the string "same". If not (e.g. "hello", 6.1, 99), return the string "different". Write an additional few lines of code that demonstrate the use of this function.

(
~same = {
  arg n1, n2, n3;
  if(
    (n1.class == n2.class) && (n1.class == n3.class),
    {"same".postln},
    {"different".postln}
  )
};
~same.value(1,3,98);
~same.value("1",3,98);
)


//Problem 8
//Write a function that checks whether a number is a multiple of 100. If so, return that number. If not, return the nearest multiple of 100. If the input  provided is not a number, return the string "not a number". Write an additional few lines of code that demonstrate the use of this function.




//Problem 9
//Write a function that checks whether an incoming value (representing a frequency memasured in Hz) is within the generally-accepted audible range of human hearing (not less than 20 and not greater than 20000). If it's within range, return the string: "play tone at xxx Hz". If it's not within this range, return the string "xxx Hz not within range". The actual number should replace the xxx in both cases. (Note: you can use the method "asString" to convert a number to a string. If the input is not a number, the function should return the string "invalid input". Write an additional few lines of code that demonstrate the use of this function.



