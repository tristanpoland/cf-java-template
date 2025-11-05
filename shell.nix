{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk17
    maven
    git
  ];

  shellHook = ''
    echo "Java development environment loaded"
    echo "Java version: $(java --version | head -1)"
    echo "Maven version: $(mvn --version | head -1)"
  '';
}