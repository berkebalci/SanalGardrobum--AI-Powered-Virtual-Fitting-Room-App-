
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState(0);

  const features = [
    {
      icon: 'ri-camera-line',
      title: 'Fotoğraf Yükle',
      desc: 'Boydan fotoğrafınızı yükleyin',
      color: 'from-purple-400 to-pink-400'
    },
    {
      icon: 'ri-body-scan-line',
      title: 'Vücut Analizi',
      desc: 'Yapay zeka ile vücut analizi',
      color: 'from-blue-400 to-cyan-400'
    },
    {
      icon: 'ri-shirt-line',
      title: 'Sanal Deneme',
      desc: 'Kıyafetleri üzerinizde görün',
      color: 'from-green-400 to-emerald-400'
    },
    {
      icon: 'ri-palette-line',
      title: 'Kombin Önerileri',
      desc: 'Size özel stil önerileri',
      color: 'from-orange-400 to-red-400'
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <img 
              src="https://public.readdy.ai/ai/img_res/f3fa907d-3a36-4d36-aff5-3ef6a1982ef1.png" 
              alt="Logo" 
              className="w-10 h-10 object-contain"
            />
            <h1 className="text-xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
              StyleAI
            </h1>
          </div>
          <button 
            onClick={() => navigate('/settings')}
            className="w-9 h-9 flex items-center justify-center rounded-full bg-gray-100 hover:bg-gray-200 transition-colors"
          >
            <i className="ri-settings-3-line text-lg text-gray-700"></i>
          </button>
        </div>
      </div>

      {/* Hero Section */}
      <div className="pt-24 pb-8 px-5">
        <div className="text-center mb-8">
          <h2 className="text-3xl font-bold text-gray-800 mb-3">
            Yapay Zeka ile<br/>Sanal Gardırop
          </h2>
          <p className="text-gray-600 text-sm leading-relaxed">
            Kıyafetlerinizi sanal olarak deneyin ve<br/>size özel stil önerileri alın
          </p>
        </div>

        {/* Main CTA */}
        <div className="relative mb-8 rounded-3xl overflow-hidden shadow-xl">
          <img 
            src="https://readdy.ai/api/search-image?query=Professional%20fashion%20photography%20of%20a%20young%20woman%20in%20casual%20elegant%20outfit%20standing%20in%20modern%20minimalist%20studio%2C%20full%20body%20shot%2C%20soft%20natural%20lighting%2C%20clean%20white%20background%2C%20fashion%20model%20pose%2C%20high-end%20fashion%20photography%20style%2C%20sharp%20details%2C%20professional%20color%20grading%2C%208k%20quality&width=375&height=400&seq=hero001&orientation=portrait"
            alt="Hero"
            className="w-full h-96 object-cover object-top"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/20 to-transparent"></div>
          <div className="absolute bottom-0 left-0 right-0 p-6">
            <button 
              onClick={() => navigate('/upload')}
              className="w-full bg-white text-gray-900 py-4 rounded-2xl font-semibold text-base shadow-lg hover:shadow-xl transition-all flex items-center justify-center gap-2"
            >
              <i className="ri-camera-fill text-xl"></i>
              Fotoğraf Yükle ve Başla
            </button>
          </div>
        </div>

        {/* Features Grid */}
        <div className="grid grid-cols-2 gap-3 mb-8">
          {features.map((feature, index) => (
            <div 
              key={index}
              className="bg-white rounded-2xl p-4 shadow-sm hover:shadow-md transition-shadow"
            >
              <div className={`w-12 h-12 rounded-xl bg-gradient-to-br ${feature.color} flex items-center justify-center mb-3`}>
                <i className={`${feature.icon} text-2xl text-white`}></i>
              </div>
              <h3 className="font-semibold text-gray-800 text-sm mb-1">{feature.title}</h3>
              <p className="text-xs text-gray-500 leading-relaxed">{feature.desc}</p>
            </div>
          ))}
        </div>

        {/* How It Works */}
        <div className="bg-white rounded-3xl p-6 shadow-sm mb-8">
          <h3 className="text-lg font-bold text-gray-800 mb-4 flex items-center gap-2">
            <i className="ri-lightbulb-flash-line text-purple-600"></i>
            Nasıl Çalışır?
          </h3>
          <div className="space-y-4">
            {[
              { step: '1', text: 'Boydan fotoğrafınızı yükleyin', icon: 'ri-upload-cloud-line' },
              { step: '2', text: 'Yapay zeka vücut analizinizi yapar', icon: 'ri-scan-line' },
              { step: '3', text: 'Gardırobunuzdaki kıyafetleri deneyin', icon: 'ri-t-shirt-line' },
              { step: '4', text: 'Size özel kombin önerileri alın', icon: 'ri-star-smile-line' }
            ].map((item, index) => (
              <div key={index} className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-pink-500 flex items-center justify-center flex-shrink-0 text-white text-sm font-bold">
                  {item.step}
                </div>
                <div className="flex-1 pt-1">
                  <p className="text-gray-700 text-sm">{item.text}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-3 gap-3 mb-8">
          {[
            { value: '10K+', label: 'Kullanıcı' },
            { value: '50K+', label: 'Deneme' },
            { value: '98%', label: 'Memnuniyet' }
          ].map((stat, index) => (
            <div key={index} className="bg-white rounded-2xl p-4 text-center shadow-sm">
              <div className="text-2xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent mb-1">
                {stat.value}
              </div>
              <div className="text-xs text-gray-500">{stat.label}</div>
            </div>
          ))}
        </div>

        {/* Recent Combinations Preview */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-bold text-gray-800">Popüler Kombinler</h3>
            <button 
              onClick={() => navigate('/combinations')}
              className="text-sm text-purple-600 font-semibold"
            >
              Tümünü Gör
            </button>
          </div>
          <div className="flex gap-3 overflow-x-auto pb-2 -mx-5 px-5 scrollbar-hide">
            {[1, 2, 3, 4].map((item) => (
              <div key={item} className="flex-shrink-0 w-40">
                <div className="bg-white rounded-2xl overflow-hidden shadow-sm">
                  <img 
                    src={`https://readdy.ai/api/search-image?query=Fashion%20outfit%20combination%20on%20mannequin%2C%20stylish%20casual%20wear%2C%20modern%20clothing%20ensemble%2C%20clean%20studio%20photography%2C%20professional%20fashion%20styling%2C%20trendy%20outfit%20display%2C%20high%20quality%20product%20photography%2C%20soft%20lighting%2C%20minimalist%20background&width=160&height=200&seq=combo00${item}&orientation=portrait`}
                    alt={`Kombin ${item}`}
                    className="w-full h-48 object-cover object-top"
                  />
                  <div className="p-3">
                    <div className="flex items-center justify-between">
                      <span className="text-xs font-semibold text-gray-700">Kombin #{item}</span>
                      <div className="flex items-center gap-1">
                        <i className="ri-star-fill text-yellow-400 text-xs"></i>
                        <span className="text-xs font-semibold text-gray-700">4.8</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 px-0 py-0 z-50">
        <div className="grid grid-cols-4 h-16">
          {[
            { icon: 'ri-home-5-fill', label: 'Ana Sayfa', path: '/' },
            { icon: 'ri-shirt-line', label: 'Gardırop', path: '/wardrobe' },
            { icon: 'ri-palette-line', label: 'Kombinler', path: '/combinations' },
            { icon: 'ri-user-line', label: 'Profil', path: '/profile' }
          ].map((tab, index) => (
            <button
              key={index}
              onClick={() => {
                setActiveTab(index);
                navigate(tab.path);
              }}
              className={`flex flex-col items-center justify-center gap-1 transition-colors ${
                activeTab === index ? 'text-purple-600' : 'text-gray-400'
              }`}
            >
              <i className={`${tab.icon} text-xl`}></i>
              <span className="text-[0.625rem] font-medium">{tab.label}</span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
