# Mars-rover

Small project demonstrating examples of IO Monad, State Monad and how to parse input in Scala.

# How to run
The simplest way to run is by invoking sbt

    $ sbt run
    
You must input the commands and press enter


It is also possible to provide a file as input
    
    $ sbt
    $ run /path/to/file
    
    
# Input
The acceptable input grammar is the following

        Setup -> Position Inputs
        Position -> [0-9]+ [0-9]+
        Inputs -> Rover Commands | Inputs
        Rover -> Position Direction
        Direction -> "N" "S" "W" "E"
        Commands -> "L" "R" "M"
        
Example 1:
        
        5 5
        1 2 N
        LMLMLMLMM        

Explanation
        
        5 5        -> First position(5 5)
        1 2 N      -> Inputs -> Rover -> Position(1 2) Direction(N)
        LMLMLMLMM  -> Inputs -> Commands
        
Example 2 - multiple inputs:
        
        5 5
        1 2 N
        LMLMLMLMM
        3 3 E
        MMRMMRMRRM    
        
        
# Output
Output of Example 1

        1 3 N
        ==========     
        
Output of Example 2

        1 3 N
        5 1 E
        ==========    