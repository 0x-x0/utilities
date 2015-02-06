'''
	Reference " Violent Python
	'''



#!/usr/bin/python

import crypt
 
def cracker(cryptPass,user):
	dict = open('dictionary.txt','r')
	print "[+] Cracking password for user : " + user + " [+]"
	salt = cryptPass.split("$")[2]
	cryptsalt = "$" + "6" + "$" + salt + "$"
	for word in dict.readlines():
		word=word.strip('\n')
		cryptWord = crypt.crypt(word,cryptsalt)
		if (cryptWord == cryptPass):
			print "[+] Found password for the user: " + user + " [+] \nPassword is: " + word + "\n"
			return
	print "Password not found for " + user + "!"  ":( \n"	
	return
 


def main():
 	passFile = open('password.txt')
        for line in passFile.readlines():
		line = line.split(":")
		user = line[0]
	    	cryptPass = line[1]
		cracker(cryptPass,user)


if __name__=="__main__":
	main()
exit(0)
