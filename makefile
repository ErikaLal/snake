default:
	@echo "Compiling..."
	javac --module-path "%JAVA_FX_HOME%"/lib --add-modules javafx.controls -sourcepath src src/*.java -d out/src

run: default
	@echo "Running..."
	java --module-path "%JAVA_FX_HOME%"/lib --add-modules javafx.controls -cp out/src; Main

clean:
	-@rm out/src/production/*.class
	-@rm out/src/*.class
