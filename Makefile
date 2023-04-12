.PHONY: all clean test

all: test coverage

test:
	./gradlew cleanTest test
	xdg-open build/reports/tests/test/index.html || open build/reports/tests/test/index.html

coverage:
	./gradlew jacocoTestReport
	xdg-open build/reports/jacoco/test/html/index.html || open build/reports/jacoco/test/html/index.html || start build/reports/jacoco/test/html/index.html

clean:
	./gradlew clean
	rm -rf build/test-results
	rm -rf build/reports
	rm -f src/main/resources/output/*.csv
