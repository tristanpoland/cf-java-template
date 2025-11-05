{
  description = "Java Test App for Cloud Foundry - Spring Boot application with interactive data visualization";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
        
        javaVersion = pkgs.jdk17;
        
        buildInputs = with pkgs; [
          javaVersion
          maven
          git
        ];

        # Build the JAR file
        java-test-app = pkgs.stdenv.mkDerivation rec {
          pname = "java-test-app";
          version = "1.0.0";
          
          src = ./.;
          
          nativeBuildInputs = buildInputs;
          
          buildPhase = ''
            export JAVA_HOME=${javaVersion}
            mvn clean package -DskipTests
          '';
          
          installPhase = ''
            mkdir -p $out/lib
            cp target/*.jar $out/lib/
            
            mkdir -p $out/bin
            cat > $out/bin/java-test-app << EOF
            #!/bin/sh
            exec ${javaVersion}/bin/java -jar $out/lib/java-test-app-${version}.jar "\$@"
            EOF
            chmod +x $out/bin/java-test-app
          '';
          
          meta = with pkgs.lib; {
            description = "Java Test App for Cloud Foundry - Spring Boot web application";
            homepage = "https://github.com/example/cf-java-template";
            license = licenses.mit;
            maintainers = [ "Tristan Poland (Trident_For_U)" ];
            platforms = platforms.all;
          };
        };

      in
      {
        packages = {
          default = java-test-app;
          java-test-app = java-test-app;
        };

        devShells = {
          default = pkgs.mkShell {
            buildInputs = buildInputs ++ (with pkgs; [
              # Development tools
              curl
              wget
              tree
              
              # Cloud Foundry CLI (if available)
              # cf-cli
            ]);

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
              export JAVA_HOME=${javaVersion}
            '';
          };
        };

        apps = {
          default = {
            type = "app";
            program = "${java-test-app}/bin/java-test-app";
          };
          
          java-test-app = {
            type = "app";
            program = "${java-test-app}/bin/java-test-app";
          };
          
          # Development commands
          build = {
            type = "app";
            program = "${pkgs.writeShellScript "build" ''
              export JAVA_HOME=${javaVersion}
              export PATH=${pkgs.lib.makeBinPath buildInputs}:$PATH
              mvn clean package
            ''}";
          };
          
          run = {
            type = "app";
            program = "${pkgs.writeShellScript "run" ''
              export JAVA_HOME=${javaVersion}
              export PATH=${pkgs.lib.makeBinPath buildInputs}:$PATH
              mvn spring-boot:run
            ''}";
          };
          
          test = {
            type = "app";
            program = "${pkgs.writeShellScript "test" ''
              export JAVA_HOME=${javaVersion}
              export PATH=${pkgs.lib.makeBinPath buildInputs}:$PATH
              mvn test
            ''}";
          };
        };
      });
}