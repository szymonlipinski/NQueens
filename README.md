
## The Problem
* Place N queens on an NxN chess board so that none of them attack each other 
(the classic n-queens problem).

* Additionally, please make sure that no three queens are in a straight line at ANY angle,
so queens on A1, C2 and E3, despite not attacking each other, form a straight line at some angle.


## The Algorithm

This is a standard NQueens solution with backtracking. The implementation uses bitboards to store the
queen position plus all controlled fields. Then all bitboards are combined using standard bit `or`.
This makes finding an empty field pretty simple. The lines between fields are calculated separately.

## Improvement Ideas

* Store the lines between the fields in a map. This is needed for fast removing them when a queen is
moved to another position.

* Store the fields laying on the additional lines in bitmaps too. This can make things faster or slower,
so it will need to be benchmarked.

## Running The Program

You can run it with `gradle run`.

## Example Output

```
Executing task 'Main.main()'...

> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes

> Task :Main.main()
Program for solving the n-queens problem.

  Place N queens on an NxN chess board so that none of them attack each other (the classic n-queens problem).   Additionally, please make sure that no three queens are in a straight line at ANY angle,   so queens on A1, C2 and E3, despite not attacking each other, form a straight line at some angle.


Provide the size of the board:
1
Searching for a solution... this can take a while...

The solution for 1 queens is:

The board with queens looks like this:

0  | * 
   | 0 

The queens are at points: [(0,0)]

Provide the size of the board:
2
Searching for a solution... this can take a while...

Didn't find any solution for 2 queens

Provide the size of the board:
10
Searching for a solution... this can take a while...

The solution for 10 queens is:

The board with queens looks like this:

9   | ·  ·  ·  ·  ·  *  ·  ·  ·  ·  
8   | ·  ·  *  ·  ·  ·  ·  ·  ·  ·  
7   | ·  ·  ·  ·  *  ·  ·  ·  ·  ·  
6   | ·  ·  ·  ·  ·  ·  ·  ·  ·  *  
5   | ·  ·  ·  ·  ·  ·  ·  *  ·  ·  
4   | ·  ·  ·  *  ·  ·  ·  ·  ·  ·  
3   | ·  *  ·  ·  ·  ·  ·  ·  ·  ·  
2   | ·  ·  ·  ·  ·  ·  *  ·  ·  ·  
1   | ·  ·  ·  ·  ·  ·  ·  ·  *  ·  
0   | *  ·  ·  ·  ·  ·  ·  ·  ·  ·  
    | 0  1  2  3  4  5  6  7  8  9  

The queens are at points: [(0,0), (1,3), (2,8), (3,4), (4,7), (5,9), (6,2), (7,5), (8,1), (9,6)]

Provide the size of the board:
```