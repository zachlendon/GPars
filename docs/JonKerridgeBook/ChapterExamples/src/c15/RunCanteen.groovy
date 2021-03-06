// GPars (formerly GParallelizer)
//
// Copyright © 2008-10  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package c15

import org.jcsp.lang.*
import org.jcsp.groovy.*
import org.jcsp.net.*
import org.jcsp.net.cns.*
import org.jcsp.net.tcpip.*
import phw.util.*
import c12.canteen.*


Node.getInstance().init(new TCPIPNodeFactory())

def cooked = CNS.createNet2One("COOKED")
def getOne = CNS.createNet2One("GETONE")
def gotOne = CNS.createOne2Net("GOTONE")

def processList = [
        new ClockedQueuingServery(service: getOne, deliver: gotOne, supply: cooked)
]

new PAR(processList).run()
