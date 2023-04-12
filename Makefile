.PHONY: all clean test

all: test

test:
	./gradlew cleanTest test
	xdg-open build/reports/tests/test/index.html || open build/reports/tests/test/index.html

clean:
	./gradlew clean
	rm -rf build/test-results
	rm -rf build/reports
	rm -f src/main/resources/output/*.csv
