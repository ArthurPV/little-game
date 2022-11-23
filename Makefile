build:
	javac -d . Vector.java
	javac -d . Turn.java
	javac -d . AI.java
	javac -d . Countdown.java
	javac -d . Game.java
	javac -d . Main.java

clean:
	rm -rf base
	rm -rf project
	rm -rf Main.class
