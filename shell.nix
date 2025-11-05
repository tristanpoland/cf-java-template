{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk17
    maven
    git
    curl
    wget
    tree
  ];

  shellHook = ''
    echo "🚀 Java Test App Development Environment"
    echo "════════════════════════════════════════"
    echo "☕ Java version: $(java --version | head -1)"
    echo "📦 Maven version: $(mvn --version | head -1)"
    echo "🔧 Git version: $(git --version)"
    echo ""
    echo "🛠️  Available commands:"
    echo "  mvn clean package    - Build the application"
    echo "  mvn spring-boot:run  - Run locally on port 8080"
    echo "  mvn test            - Run tests"
    echo "  ./deploy.sh         - Deploy to Cloud Foundry"
    echo ""
    echo "📁 Project structure:"
    tree -L 2 -I 'target|.git' . 2>/dev/null || ls -la
    echo ""
    
    export JAVA_HOME=${pkgs.jdk17}
  '';
}