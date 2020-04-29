import requests

URL = "http://localhost:8080"
API_ROOT = "/api/call/"

def createNewCall(caller_number,callee_number,call_type):
	# request POST @ /api/call/createNewCall

	req_body = {
		"num_caller" : caller_number,
		"num_callee" : callee_number,
		"type" : call_type
	}

	r = requests.post(url=(URL+API_ROOT+"createCall"), json=req_body)

	return r.text

def endCall(call_id):
	# request PUT @ /api/call/endCall

	r = requests.put(url=(URL+API_ROOT+"endCall"), data={"call_id":call_id})

	return r.text

def deleteCall(call_id):
	# request DELETE @ /api/call/deleteCall

	r = requests.delete(url=(URL+API_ROOT+"deleteCall"), data={"call_id":call_id})

	return r.text

def getAllCalls(filter=None,limit=None,offset=None):
	# request GET @ /api/call/getAllCalls

	get_params = {}

	if(limit and offset):
		get_params["limit"] = limit
		get_params["offset"] = limit
	if(filter):
		get_params["call_type"] = filter

	r = requests.get(url=(URL+API_ROOT+"getAllCalls"), params=get_params)

	return r.text

def statsTotalCallDuration(filter):
	# request GET @ /api/stats/totalCallDuration
	get_params = {"call_type":filter}
	r = requests.get(url=(URL+API_ROOT+"stats/totalCallDuration"), params=get_params)
	return r.text

def statsTotalNumberOfCalls():
	# request GET @ /api/stats/totalNumberOfCalls
	r = requests.get(url=(URL+API_ROOT+"stats/totalNumberOfCalls"))
	return r.text

def statsNumberOfCallsCaller(caller_number):
	# request GET @ /api/stats/numberOfCallsCaller
	r = requests.get(url=(URL+API_ROOT+"stats/numberOfCallsCaller"),  params={"caller_number":caller_number})
	return r.text

def statsNumberOfCallsCallee(callee_number):
	# request GET @ /api/stats/numberOfCallsCallee
	r = requests.get(url=(URL+API_ROOT+"stats/numberOfCallsCallee"),  params={"callee_number":callee_number})
	return r.text

def statsTotalCallCost():
	# request GET @ /api/stats/getTotalCallCost
	r = requests.get(url=(URL+API_ROOT+"stats/getTotalCallCost"))
	return r.text

def displayCreateCallMenu():
	print("# Create new call")
	caller_number = input("## Caller number: ")
	callee_number = input("## Callee number: ")
	call_type = input("## Call type (INBOUND or OUTBOUND): ")
	return createNewCall(caller_number,callee_number,call_type)

def displayEndCallMenu():
	print("# End ongoing call")
	call_id = input("## Call id: ")
	return endCall(call_id)

def displayDeleteCallMenu():
	print("# Delete call")
	call_id = input("## Call id: ")
	return deleteCall(call_id)

def displayGetAllCallsMenu():
	print("# Get all calls")
	print("## Choose filter")
	print("## 1. INBOUND")
	print("## 2. OUTBOUND")
	print("## 3. No filter")
	print("0. Exit")
	option = input("> Option: ")
	if(option == "1"):
		print("## Getting all calls of type: INBOUND")
		paging_params = displayPagingMenu()
		return getAllCalls("INBOUND", paging_params[0], paging_params[1])
	elif(option == "2"):
		print("## Getting all calls of type: OUTBOUND")
		paging_params = displayPagingMenu()
		return getAllCalls("OUTBOUND", paging_params[0], paging_params[1])
	elif(option == "3"):
		print("## Getting all calls")
		paging_params = displayPagingMenu()
		return getAllCalls(limit=paging_params[0], offset=paging_params[1])
	elif(option == "0"):
		return
	else:
		print("Invalid operation")

def displayStatsTotalCallDurationMenu():
	print("# Stats - Get all calls duration")
	filter = input("> Filter (INBOUND / OUTBOUND): ")
	return statsTotalCallDuration(filter)

def displayStatsMenu():
	print("# Statistics operations (total aggregated by day)")
	print("## 1. Total call duration (filtered by type)")
	print("## 2. Total number of calls")
	print("## 3. Number of calls by Caller Number")
	print("## 4. Number of calls by Callee Number")
	print("## 5. Total call cost")
	print("0. Exit")
	option = input("> Option: ")

	if(option=="1"):
		return displayStatsTotalCallDurationMenu()
	elif(option=="2"):
		return statsTotalNumberOfCalls()
	elif(option=="3"):
		caller_number = input(">> Caller number: ")
		return statsNumberOfCallsCaller(caller_number)
	elif(option=="4"):
		callee_number = input(">> Callee number: ")
		return statsNumberOfCallsCallee(callee_number)
	elif(option=="5"):
		return statsTotalCallCost()
	elif(option=="0"):
		return
	else:
		print("Invalid operation")

	return

def displayPagingMenu():
	print("Display result using pagination ? (Y/N)")
	option = input("> Option: ")
	if(option == "Y"):
		limit = input(">> Limit: ")
		offset = input(">> Offset: ")
		return (limit,offset)
	elif(option == "N"):
		return None,None
	else:
		print("Invalid operation")
		return None,None

def printMainMenu():
	print("-----------------------------------------------------------------")
	print(">> Client application operations <<")
	print("1. Create new call ")
	print("2. End ongoing call")
	print("3. Delete call")
	print("4. Get all calls")
	print("5. Get statistics")
	print("0. Exit")
	option = input("> Option: ")
	return option

def main():
	while True:
		option = printMainMenu()

		if(option == "1"):
			response = displayCreateCallMenu()
			print(">>> Server returned: " + response)

		elif(option == "2"):
			response = displayEndCallMenu()
			print(">>> Server returned: " + response)
			
		elif(option == "3"):
			response = displayDeleteCallMenu()
			print(">>> Server returned: " + response)

		elif(option == "4"):
			response = displayGetAllCallsMenu()
			print(">>> Server returned: " + response)
		
		elif(option == "5"):
			response = displayStatsMenu()
			print(">>> Server returned: " + response)
			
		elif(option == "0"):
			break

		else:
			print("Invalid operation!")
			

if __name__ == "__main__":
	main()