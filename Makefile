.PHONY: test eastwood cljfmt cloverage release deploy clean

VERSION ?= 1.9

# Some tests need to be filtered based on JVM version.  This selector
# will be mapped to a function in project.clj, and that function
# determines which `deftest` to run based on their metadata.
TEST_SELECTOR = :java$(shell lein version | cut -d " " -f 5 | cut -d "." -f 1-2)

test:
	lein with-profile +$(VERSION) test $(TEST_SELECTOR)

eastwood:
	lein with-profile +$(VERSION),+eastwood eastwood

cljfmt:
	lein with-profile +$(VERSION),+cljfmt cljfmt check

cloverage:
	lein with-profile +$(VERSION),+cloverage cloverage --codecov

# When releasing, the BUMP variable controls which field in the
# version string will be incremented in the *next* snapshot
# version. Typically this is either "major", "minor", or "patch".

BUMP ?= patch

release:
	lein with-profile +$(VERSION) release $(BUMP)

# Deploying requires the caller to set environment variables as
# specified in project.clj to provide a login and password to the
# artifact repository.  We're setting TRAVIS_PULL_REQUEST to a default
# value to avoid accidentally deploying from the command line. Inside
# Travis CI this variable will be set to the pull request number, or
# to "false" if it's not a pull request.

TRAVIS_PULL_REQUEST ?= "true"

deploy:
	if [ "$(TRAVIS_PULL_REQUEST)" != "false" ]; then \
	    echo "Pull request detected. Skipping deploy."; \
	else \
	    lein with-profile +$(VERSION) deploy clojars; \
	fi

clean:
	lein clean
