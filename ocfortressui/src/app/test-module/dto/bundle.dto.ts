export type Bundles = {
  bundleSelected: boolean,
  bundleId:number,
  bundleName:string,
  description:string,
  statusCd:string,
  recentJobId:string,
  lastExecutionResult:string;
  lastExecutedBy:string;
  lastExecutedOn:string;
  testCaseBundle:TestCaseMap[]
};

export type TestCaseMap = {
  testCaseId: string;
  testCaseName: string;
}

