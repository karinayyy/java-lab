# Java Lab

[Link to course](http://www.iwanoff.inf.ua/java_ua_2/index.html)

The course "Advanced Java Programming Course" is devoted to the theoretical and practical aspects of using Java language tools and the JavaSE platform. For students of various specialties

## Laboratory work 1
Work with dates and text. Localization

#### 1.1 Індивідуальне завдання

My number on the list is 8

Design and implement classes to represent the essence of [the second laboratory work](http://www.iwanoff.inf.ua/java_ua/LabTraining02.html#Tasks) of the "Fundamentals of Java Programming" course. The solution should be based on the previously created class hierarchy.

You should create a derived class that represents the base entity. How to basically use the class created in [the third lab](http://www.iwanoff.inf.ua/java_ua/LabTraining03.html#Tasks) of the course "Basics of Java Programming". The class should be supplemented with the ability to support various localizations, in particular, Ukrainian and American. It is necessary to provide for the translation of text, the output of numbers, as well as dates and times, taking into account different localizations. Add (modify) search for words in comments (or other text) using regular expressions. Sort entities alphabetically using the Collator class.

Create a derived class from the class that represents the second entity, in which to add a field - the time and date when a certain event occurs. To represent the time and date, use the classes of the java.time package (taking into account the time zone). Calculate the time intervals between events and find and output the smallest of the intervals. If the class that represented the second entity of the individual task did not contain a field of type String, you should add a comment field to the event. For a new (or existing) text field, provide for the possibility of output in Ukrainian or English, depending on localization.

The program must demonstrate:

- reproduction of the implementation of tasks of laboratory works No. 2 and No. 3 of the course "Fundamentals of Java Programming";
- formatting of numerical data in various ways, as well as taking into account localization;
- output of data about dates and times of events taking into account localization;
- text output in Ukrainian and English;
- alphabetical sorting capabilities using the Collator class;
- calculation and output of time intervals between events related to the second essence of the task; finding and deriving the smallest of the intervals;
- variants of complex search in the text (using regular expressions), in particular, searching for a fragment of text at the beginning (at the end) of a word.

#### 1.2 Date entry

Implement a program in which the user enters a string. The program checks whether the string corresponds to the date representation accepted in Ukraine. The check is carried out using regular expressions. If the string does not meet the requirements, an error message is displayed. Otherwise, Date, GregorianCalendar, and LocalDate objects are created and output to the console.

#### 1.3 Phone number verification

Develop a program to verify the correctness of the fact that the line is the telephone number of the Kyivstar operator. Regular expressions should be used.

#### 1.4 Checking the password string

Develop a program for checking password compliance with the requirements:

- the password can contain letters of the Latin alphabet, numbers and special symbols: _ - *;
- there must be at least one lowercase letter;
- there must be at least one capital letter;
- must be at least one digit;
- there must be at least one special character.

Regular expressions should be used.
#### 1.5 Obtaining an array of substrings (additional task)

A string longer than 20 characters contains letters and numbers. Get from this string an array of substrings that contain letters between numbers (groups of numbers), define numbers as separators.
#### 1.6 Using the BigInteger class (additional task)

Write a program that fills a BigInteger type number with random numbers and calculates the integer power of this number. Use BigInteger for the result. Implement two options - using the pow class function and the function that provides multiplication of long integers. Compare the results.

## Conclusions

The first task took longer to complete than all the other tasks, but this was not due to the difficulty of the material. I just had to spend a lot of time understanding the requirements of the task and reviewing the code I wrote last semester. Overall, this work was very useful.

  

Localization was an interesting part of the job. At first I didn't understand where to store the .properties files to work properly with localization, but I used the concept of storing key-value pairs and using them in the code. Also, the technique was very useful and helped me.

 

Completing the regular expression tasks were also fun and easy, requiring you to come up with the right logic and represent it in your code. This was my first experience with regular expressions, so I don't know all the ins and outs yet. Care must be taken when testing expressions to avoid incorrect validation results.
