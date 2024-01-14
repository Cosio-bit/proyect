terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = "dop_v1_6afc113fd382c84c80e813f90d640286467bbd1d2a195dac098d0e6611a8c193"
}

resource "digitalocean_droplet" "web" {
  image  = "ubuntu-20-04-x64"
  name   = "web-1"
  region = "nyc3"
  size   = "s-1vcpu-2gb"
  ssh_keys = ["89:7a:1b:1e:45:6c:25:75:8a:8e:b4:c3:fc:d5:50:82"]
}