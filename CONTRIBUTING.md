# Contributing to Price Tracker

Price Tracker is released under the [Apache 2 license](LICENCE.txt). If you would like to contribute
something, or simply want to hack on the code this document should help you get started.

## Code of Conduct

This project adheres to the Contributor Covenant [code of conduct](CODE_OF_CONDUCT.md).
By participating, you are expected to uphold this code.

Please report unacceptable behavior to <opensource@nhsbsa.nhs.uk>

## Using Gitlab Issues

We use Gitlab issues to track community reported bugs and enhancements.

If you are reporting a bug, please help to speed up problem diagnosis by providing as
much information as possible. Ideally, that would include a small sample project that
reproduces the problem.

## Reporting Security Vulnerabilities

If you think you have found a security vulnerability in this codebase please follow
our guidance on [reporting security vulnerabilities](SECURITY.md).

## Contributor privacy

The NHSBSA take privacy seriously. All contributors may protect their identity when pushing code to our open sourced repositories by choosing an anonymous name and email. Contributors are responsible for configuring their local Git and Gitlab account to preserve their privacy. Commits containing contributor identifiable information may not be removed by rewriting history, once merged into `main`.

## A quick guide on how to contribute

1. Fork the project

2. Clone the repo from your own space

3. Configure [secrets detection](/SECRETS.md)

4. Use [README.md](/README.md) instructions to build/run.

5. Create a branch

6. Run the tests. Functional merge requests must include unit tests,
   so it is good to ensure you've got them passing to begin with.

7. Add your functionality or bug fix and a test for your change. Only refactoring and
   documentation changes do not require tests.

8. Make sure all the tests pass.

9. Push to your fork, and submit a merge request.

## Cloning the git repository on Windows

Some files in the git repository may exceed the Windows maximum file path (260
characters), depending on where you clone the repository. If you get `Filename too long`
errors, set the `core.longPaths=true` git option:

```bash
git clone -c core.longPaths=true {project URL}
```

## Comments on this policy

If you have suggestions on how this policy could be improved, please submit a pull request.
