#!/bin/bash

# Cloud Foundry Deployment Script for Java Test App
# This script builds and deploys the Java application to Cloud Foundry

set -e

echo "🚀 Starting Java Test App deployment to Cloud Foundry..."

# Check if we're in a Nix environment, if not, enter it
if [ -z "$IN_NIX_SHELL" ]; then
    echo "📦 Entering Nix shell environment..."
    exec nix-shell --run "$0 $*"
fi

echo "☕ Building application with Maven..."
mvn clean package -DskipTests

echo "📋 Checking if Cloud Foundry CLI is available..."
if ! command -v cf &> /dev/null; then
    echo "❌ Cloud Foundry CLI not found. Please install cf CLI first."
    echo "   Visit: https://docs.cloudfoundry.org/cf-cli/install-go-cli.html"
    exit 1
fi

echo "📊 Checking if you're logged into Cloud Foundry..."
if ! cf target &> /dev/null; then
    echo "❌ Not logged into Cloud Foundry. Please run:"
    echo "   cf login -a <API_ENDPOINT> -o <ORG> -s <SPACE>"
    exit 1
fi

echo "🔍 Current Cloud Foundry target:"
cf target

echo "🚀 Deploying application to Cloud Foundry..."
cf push

echo "✅ Deployment completed successfully!"
echo "📱 Your application should be available at the URL shown above."
echo "🔍 Check application status with: cf app java-test-app"
echo "📋 View logs with: cf logs java-test-app --recent"